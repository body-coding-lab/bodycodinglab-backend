package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.user.request.DeleteUserRequestDto;
import com.bcl.fitmate.backend.dto.user.request.UpdateMemberInfoRequestDto;
import com.bcl.fitmate.backend.dto.user.request.UpdateTrainerInfoRequestDto;
import com.bcl.fitmate.backend.dto.user.response.GetMemberInfoResponseDto;
import com.bcl.fitmate.backend.dto.user.response.GetTrainerInfoResponseDto;
import com.bcl.fitmate.backend.dto.user.response.GetUserInfoResponseDto;
import com.bcl.fitmate.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    @Override
    public ResponseDto<GetUserInfoResponseDto> getUserInfo(Long id) {
        return null;
    }

    @Override
    public ResponseDto<GetMemberInfoResponseDto> getMemberInfo(Long id) {
        return null;
    }

    @Override
    public ResponseDto<GetMemberInfoResponseDto> updateMemberInfo(Long id, UpdateMemberInfoRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<GetTrainerInfoResponseDto> getTrainerInfo(Long id) {
        return null;
    }

    @Override
    public ResponseDto<GetTrainerInfoResponseDto> updateTrainerInfo(Long id, UpdateTrainerInfoRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<Void> deleteUser(Long id, DeleteUserRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<Void> updateProfileImage(Long id, MultipartFile profileImage) {
        return null;
    }

    @Override
    public ResponseDto<Void> deleteProfileImage(Long id) {
        return null;
    }
}
