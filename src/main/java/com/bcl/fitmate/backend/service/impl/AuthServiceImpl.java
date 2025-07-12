package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.common.enums.member.MemberStatus;
import com.bcl.fitmate.backend.common.enums.trainer.TrainerStatus;
import com.bcl.fitmate.backend.common.enums.uploadFile.TargetType;
import com.bcl.fitmate.backend.common.enums.user.UserRole;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.auth.request.*;
import com.bcl.fitmate.backend.dto.auth.response.*;
import com.bcl.fitmate.backend.entity.*;
import com.bcl.fitmate.backend.provider.JwtProvider;
import com.bcl.fitmate.backend.repository.MemberRepository;
import com.bcl.fitmate.backend.repository.RoleRepository;
import com.bcl.fitmate.backend.repository.TrainerRepository;
import com.bcl.fitmate.backend.repository.UserRepository;
import com.bcl.fitmate.backend.service.AuthService;
import com.bcl.fitmate.backend.service.MailService;
import com.bcl.fitmate.backend.service.UploadFileService;
import com.bcl.fitmate.backend.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final UploadFileService uploadFileService;
    private final MailService mailService;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public ResponseDto<SignUpMemberResponseDto> signUpMember(SignUpMemberRequestDto dto, MultipartFile profileImage) throws IOException {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            return ResponseDto.fail(ResponseCode.DUPLICATED_USER_ID, ResponseMessage.DUPLICATED_USER_ID);
        }

        if (userRepository.findByPhone(dto.getPhone()).isPresent()) {
            return ResponseDto.fail(ResponseCode.DUPLICATED_TEL_NUMBER, ResponseMessage.DUPLICATED_TEL_NUMBER);
        }

        if (userRepository.findByEmail(dto.getPhone()).isPresent()) {
            return ResponseDto.fail(ResponseCode.DUPLICATED_EMAIL, ResponseMessage.DUPLICATED_EMAIL);
        }

        if(!dto.getPassword().equals(dto.getConfirmPassword())) {
            return ResponseDto.fail(ResponseCode.NOT_MATCH_PASSWORD, ResponseMessage.NOT_MATCH_PASSWORD);
        }

        Role userRole = roleRepository.findByName(UserRole.MEMBER)
                .orElseGet(() -> roleRepository.save(Role.builder().name(UserRole.MEMBER).build()));

        User user = User.builder()
                .role(userRole)
                .username(dto.getUsername())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .birthdate(dto.getBirthdate())
                .gender(dto.getGender())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .build();
        userRepository.save(user);

        if (profileImage != null && !profileImage.isEmpty()) {
            user.setProfileImage(uploadFileService.saveSingleFile(profileImage, user.getId(), TargetType.PROFILE));
        }

        Member member = Member.builder()
                .user(user)
                .memberAddress(dto.getMemberAddress())
                .oneDayTicketCount(3)
                .status(MemberStatus.NOT_SUBSCRIPTION)
                .build();
        memberRepository.save(member);

        SignUpMemberResponseDto data = SignUpMemberResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    @Transactional
    public ResponseDto<SignUpTrainerResponseDto> signUpTrainer(SignUpTrainerRequestDto dto, MultipartFile attachmentFile, MultipartFile profileImage) throws IOException {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            return ResponseDto.fail(ResponseCode.DUPLICATED_USER_ID, ResponseMessage.DUPLICATED_USER_ID);
        }

        if (userRepository.findByPhone(dto.getPhone()).isPresent()) {
            return ResponseDto.fail(ResponseCode.DUPLICATED_TEL_NUMBER, ResponseMessage.DUPLICATED_TEL_NUMBER);
        }

        if (userRepository.findByEmail(dto.getPhone()).isPresent()) {
            return ResponseDto.fail(ResponseCode.DUPLICATED_EMAIL, ResponseMessage.DUPLICATED_EMAIL);
        }

        if(!dto.getPassword().equals(dto.getConfirmPassword())) {
            return ResponseDto.fail(ResponseCode.NOT_MATCH_PASSWORD, ResponseMessage.NOT_MATCH_PASSWORD);
        }

        Role userRole = roleRepository.findByName(UserRole.TRAINER)
                .orElseGet(() -> roleRepository.save(Role.builder().name(UserRole.TRAINER).build()));

        User user = User.builder()
                .role(userRole)
                .username(dto.getUsername())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .birthdate(dto.getBirthdate())
                .gender(dto.getGender())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .build();
        userRepository.save(user);

        if (profileImage != null && !profileImage.isEmpty()) {
            user.setProfileImage(uploadFileService.saveSingleFile(profileImage, user.getId(), TargetType.PROFILE));
        }

        Trainer trainer = Trainer.builder()
                .user(user)
                .jobAddress(dto.getJobAddress())
                .trainerStatus(TrainerStatus.PENDING)
                .build();
        trainerRepository.save(trainer);

        trainer.setAttachmentFile(uploadFileService.saveSingleFile(attachmentFile, trainer.getId(), TargetType.ATTACHMENT));
        trainerRepository.save(trainer);

        SignUpTrainerResponseDto data = SignUpTrainerResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<? extends LoginUserResponseDto> login(LoginUserRequestDto dto) {
        User user = userService.getUserByUsername(dto.getUsername());

        if (!checkPassword(user, dto.getPassword())) {
            return ResponseDto.fail(ResponseCode.NOT_CORRECT_PASSWORD, ResponseMessage.NOT_CORRECT_PASSWORD);
        }

        String profileImageUrl = null;
        UploadFile profileImage = user.getProfileImage();
        if (profileImage != null) {
            profileImageUrl = ApiMappingPattern.FILE_API + "/single/" + profileImage.getId();
        }

        String token = jwtProvider.generateJwtToken(user.getId(), user.getRole().getName());

        if (user.getRole().getName().equals(UserRole.TRAINER) && user.getTrainer().getTrainerStatus() == TrainerStatus.REJECTED) {
            LoginRejectedTrainerResponseDto data  = LoginRejectedTrainerResponseDto.builder()
                    .token(token)
                    .exprTime(jwtProvider.getExpirationMs())
                    .id(user.getId())
                    .role(user.getRole().getName())
                    .username(user.getUsername())
                    .name(user.getName())
                    .profileImageUrl(profileImageUrl)
                    .trainerStatus(user.getTrainer().getTrainerStatus())
                    .build();

            return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
        }

        LoginUserResponseDto data = LoginUserResponseDto.builder()
                .token(token)
                .exprTime(jwtProvider.getExpirationMs())
                .id(user.getId())
                .role(user.getRole().getName())
                .username(user.getUsername())
                .name(user.getName())
                .profileImageUrl(profileImageUrl)
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<RecoverUsernameResponseDto> recoverUsername(RecoverUsernameRequestDto dto) {
        User user = userService.getUserByEmail(dto.getEmail());

        if (!user.getName().equals(dto.getName()) || !user.getBirthdate().equals(dto.getBirthdate())) {
            return ResponseDto.fail(ResponseCode.NOT_MATCH_INFORMATION, ResponseMessage.NOT_MATCH_INFORMATION);
        }

        RecoverUsernameResponseDto data = new RecoverUsernameResponseDto(user.getUsername());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<GetResetPasswordUserResponseDto> getResetPasswordUser(GetResetPasswordUserRequestDto dto) {
        User user = userService.getUserByUsername(dto.getUsername());

        if (!user.getName().equals(dto.getName())
                || !user.getBirthdate().equals(dto.getBirthdate())
                || !user.getEmail().equals(dto.getEmail())
        ) {
            return ResponseDto.fail(ResponseCode.NOT_MATCH_INFORMATION, ResponseMessage.NOT_MATCH_INFORMATION);
        }

        GetResetPasswordUserResponseDto data = GetResetPasswordUserResponseDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    @Transactional
    public ResponseDto<Void> resetPassword(String token, ResetPasswordRequestDto dto) {
        String email = jwtProvider.getEmailFromJwtToken(token);

        if (email == null) {
            return ResponseDto.fail(ResponseCode.INVALID_TOKEN, ResponseMessage.INVALID_TOKEN);
        }

        User user = userService.getUserByEmail(email);

        if (!dto.getNewPassword().equals(user.getPassword())) {
            return ResponseDto.fail(ResponseCode.NOT_MATCH_PASSWORD, ResponseMessage.NOT_MATCH_PASSWORD);
        }

        user.setPassword(bCryptPasswordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    public ResponseDto<Void> requestResetPasswordEmail(SendResetPasswordEmailRequestDto dto) throws MessagingException {
        String token = jwtProvider.generateResetPasswordJwtToken(dto.getEmail());
        mailService.sendResetPasswordEmail(dto.getEmail(), token);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<Void> verifyEmail(String token) {
        if (token == null) {
            return ResponseDto.fail(ResponseCode.MISSING_TOKEN, ResponseMessage.MISSING_TOKEN);
        }

        String email = jwtProvider.getEmailFromJwtToken(token);
        boolean isEmailVerified = checkEmail(email);

        if (!isEmailVerified) {
            return ResponseDto.fail(ResponseCode.NO_EXIST_EMAIL, ResponseMessage.NO_EXIST_EMAIL);
        }

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    public boolean checkPassword(User user, String password) {
        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }

    @Override
    public boolean checkEmail(String email) {
        User user = userService.getUserByEmail(email);
        return user != null;
    }
}
