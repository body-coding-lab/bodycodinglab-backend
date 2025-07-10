package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.common.enums.member.MemberStatus;
import com.bcl.fitmate.backend.common.enums.trainer.TrainerStatus;
import com.bcl.fitmate.backend.common.enums.uploadFile.TargetType;
import com.bcl.fitmate.backend.common.enums.user.UserRole;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.auth.request.*;
import com.bcl.fitmate.backend.dto.auth.response.*;
import com.bcl.fitmate.backend.entity.Member;
import com.bcl.fitmate.backend.entity.Role;
import com.bcl.fitmate.backend.entity.Trainer;
import com.bcl.fitmate.backend.entity.User;
import com.bcl.fitmate.backend.provider.JwtProvider;
import com.bcl.fitmate.backend.repository.MemberRepository;
import com.bcl.fitmate.backend.repository.RoleRepository;
import com.bcl.fitmate.backend.repository.TrainerRepository;
import com.bcl.fitmate.backend.repository.UserRepository;
import com.bcl.fitmate.backend.service.AuthService;
import com.bcl.fitmate.backend.service.UploadFileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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
    private final UploadFileService uploadFileService;
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
    public ResponseDto<? extends LoginUserResponseDto> login(LoginUserRequestDto dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElse(null);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.NO_EXIST_USER_ID, ResponseMessage.NO_EXIST_USER_ID);
        }

//        if (!checkPassword())
        return null;
    }

    @Override
    public ResponseDto<RecoverUsernameResponseDto> recoverUsername(RecoverUsernameRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<GetResetPasswordUserResponseDto> getResetPasswordUser(GetResetPasswordUserRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<Void> resetPassword(String token, ResetPasswordRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<Void> requestResetPasswordEmail(SendResetPasswordEmailRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<Void> verifyEmail(String token) {
        return null;
    }

    @Override
    public boolean checkPassword(User user, String password) {
        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }

    @Override
    public boolean checkEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
