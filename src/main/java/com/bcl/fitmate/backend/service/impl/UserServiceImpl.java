package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.common.enums.uploadFile.TargetType;
import com.bcl.fitmate.backend.common.enums.user.UserRole;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.user.request.DeleteUserRequestDto;
import com.bcl.fitmate.backend.dto.user.request.UpdateMemberInfoRequestDto;
import com.bcl.fitmate.backend.dto.user.request.UpdateTrainerInfoRequestDto;
import com.bcl.fitmate.backend.dto.user.response.GetMemberInfoResponseDto;
import com.bcl.fitmate.backend.dto.user.response.GetTrainerInfoResponseDto;
import com.bcl.fitmate.backend.dto.user.response.GetUserInfoResponseDto;
import com.bcl.fitmate.backend.entity.UploadFile;
import com.bcl.fitmate.backend.entity.User;
import com.bcl.fitmate.backend.repository.UserRepository;
import com.bcl.fitmate.backend.service.AuthService;
import com.bcl.fitmate.backend.service.UploadFileService;
import com.bcl.fitmate.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UploadFileService uploadFileService;
    private final AuthService authService;

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<GetUserInfoResponseDto> getUserInfo(Long id) {
        User user = getUserById(id);

        String profileImageUrl = null;
        UploadFile profileImage = user.getProfileImage();
        if (profileImage != null) {
            profileImageUrl = ApiMappingPattern.FILE_API + "/single/" + profileImage.getId();
        }

        GetUserInfoResponseDto data = GetUserInfoResponseDto.builder()
                .id(user.getId())
                .role(user.getRole().getName().name())
                .username(user.getUsername())
                .name(user.getName())
                .profileImageUrl(profileImageUrl)
                .trainerStatus(
                        user.getRole().getName().equals(UserRole.TRAINER)
                                ? user.getTrainer().getTrainerStatus()
                                : null
                )
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseCode.SUCCESS, data);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<GetMemberInfoResponseDto> getMemberInfo(Long id) {
        User user = getUserById(id);

        GetMemberInfoResponseDto data = GetMemberInfoResponseDto.builder()
                .username(user.getUsername())
                .name(user.getName())
                .birthdate(user.getBirthdate())
                .gender(user.getGender())
                .phone(user.getPhone())
                .email(user.getEmail())
                .memberAddress(user.getMember().getMemberAddress())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseCode.SUCCESS, data);
    }

    @Override
    @Transactional
    public ResponseDto<Void> updateMemberInfo(Long id, UpdateMemberInfoRequestDto dto) {
        User user = getUserById(id);

        if (dto.getName() != null && !dto.getName().isEmpty()) {
            user.setName(dto.getName());
        }

        if (dto.getMemberAddress() != null && !dto.getMemberAddress().isEmpty()) {
            user.getMember().setMemberAddress(dto.getMemberAddress());
        }
        userRepository.save(user);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseCode.SUCCESS);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<GetTrainerInfoResponseDto> getTrainerInfo(Long id) {
        User user = getUserById(id);
        String attachmentFileUrl = null;
        UploadFile attachmentFile = user.getTrainer().getAttachmentFile();
        if (attachmentFile != null) {
            attachmentFileUrl = ApiMappingPattern.FILE_API + "/single/" + attachmentFile.getId();
        }

        GetTrainerInfoResponseDto data = GetTrainerInfoResponseDto.builder()
                .trainerId(user.getTrainer().getId())
                .username(user.getUsername())
                .name(user.getName())
                .birthdate(user.getBirthdate())
                .gender(user.getGender())
                .phone(user.getPhone())
                .email(user.getEmail())
                .jobAddress(user.getTrainer().getJobAddress())
                .attachmentFileUrl(attachmentFileUrl)
                .trainerStatus(user.getTrainer().getTrainerStatus())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    @Transactional
    public ResponseDto<Void> updateTrainerInfo(Long id, UpdateTrainerInfoRequestDto dto) {
        User user = getUserById(id);

        user.setName(dto.getName());
        userRepository.save(user);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    @Transactional
    public ResponseDto<Void> deleteUser(Long id, DeleteUserRequestDto dto) {
        User user = getUserById(id);

        if (!dto.getDeleteMessage().equals("탈퇴하겠습니다.")) {
            return ResponseDto.fail(ResponseCode.INVALID_INPUT, ResponseCode.INVALID_INPUT);
        }

        if (!authService.checkPassword(user, dto.getPassword())) {
            return ResponseDto.fail(ResponseCode.NOT_CORRECT_PASSWORD, ResponseCode.NOT_CORRECT_PASSWORD);
        }

        userRepository.delete(user);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    @Transactional
    public ResponseDto<Void> updateProfileImage(Long id, MultipartFile profileImage) throws IOException {
        User user = getUserById(id);

        if (profileImage == null || profileImage.isEmpty()) {
            return ResponseDto.fail(ResponseCode.FILE_NOT_ATTACHED, ResponseCode.FILE_NOT_ATTACHED);
        }

        user.setProfileImage(uploadFileService.updateSingleFile(user.getProfileImage().getId(), user.getId(), TargetType.PROFILE, profileImage));

        return ResponseDto.success(ResponseMessage.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    @Transactional
    public ResponseDto<Void> deleteProfileImage(Long id) {
        User user = getUserById(id);

//        uploadFileService.deleteFile(user.getProfileImage().getId());
        user.setProfileImage(null);
        userRepository.save(user);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NO_EXIST_USER_ID));
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NO_EXIST_EMAIL));
    }
}
