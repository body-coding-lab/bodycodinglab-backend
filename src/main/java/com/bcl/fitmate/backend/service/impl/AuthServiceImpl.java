package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.auth.request.*;
import com.bcl.fitmate.backend.dto.auth.response.GetResetPasswordUserResponseDto;
import com.bcl.fitmate.backend.dto.auth.response.LoginUserResponseDto;
import com.bcl.fitmate.backend.dto.auth.response.RecoverUsernameResponseDto;
import com.bcl.fitmate.backend.dto.auth.response.SignUpMemberResponseDto;
import com.bcl.fitmate.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public ResponseDto<SignUpMemberResponseDto> signUpMember(SignUpMemberRequestDto dto, MultipartFile profileImage) {
        return null;
    }

    @Override
    public ResponseDto<SignUpMemberResponseDto> signUpTrainer(SignUpTrainerRequestDto dto, MultipartFile attachmentFile, MultipartFile profileImage) {
        return null;
    }

    @Override
    public ResponseDto<? extends LoginUserResponseDto> login(LoginUserRequestDto dto) {
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
    public ResponseDto<String> resetPassword(String token, ResetPasswordRequestDto dto) {
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
}
