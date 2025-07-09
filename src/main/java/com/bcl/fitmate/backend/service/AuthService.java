package com.bcl.fitmate.backend.service;

import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.auth.request.*;
import com.bcl.fitmate.backend.dto.auth.response.*;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

public interface AuthService {
    ResponseDto<SignUpMemberResponseDto> signUpMember(@Valid SignUpMemberRequestDto dto, MultipartFile profileImage);
    ResponseDto<SignUpTrainerResponseDto> signUpTrainer(@Valid SignUpTrainerRequestDto dto, MultipartFile attachmentFile, MultipartFile profileImage);
    ResponseDto<? extends LoginUserResponseDto> login(@Valid LoginUserRequestDto dto);
    ResponseDto<RecoverUsernameResponseDto> recoverUsername(@Valid RecoverUsernameRequestDto dto);
    ResponseDto<GetResetPasswordUserResponseDto> getResetPasswordUser(@Valid GetResetPasswordUserRequestDto dto);
    ResponseDto<Void> resetPassword(String token, @Valid ResetPasswordRequestDto dto);
    ResponseDto<Void> requestResetPasswordEmail(@Valid SendResetPasswordEmailRequestDto dto);
    ResponseDto<Void> verifyEmail(String token);
}
