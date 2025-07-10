package com.bcl.fitmate.backend.service;

import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.auth.request.*;
import com.bcl.fitmate.backend.dto.auth.response.*;
import com.bcl.fitmate.backend.entity.User;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AuthService {
    ResponseDto<SignUpMemberResponseDto> signUpMember(@Valid SignUpMemberRequestDto dto, MultipartFile profileImage) throws IOException;
    ResponseDto<SignUpTrainerResponseDto> signUpTrainer(@Valid SignUpTrainerRequestDto dto, MultipartFile attachmentFile, MultipartFile profileImage) throws IOException;
    ResponseDto<? extends LoginUserResponseDto> login(@Valid LoginUserRequestDto dto);
    ResponseDto<RecoverUsernameResponseDto> recoverUsername(@Valid RecoverUsernameRequestDto dto);
    ResponseDto<GetResetPasswordUserResponseDto> getResetPasswordUser(@Valid GetResetPasswordUserRequestDto dto);
    ResponseDto<Void> resetPassword(String token, @Valid ResetPasswordRequestDto dto);
    ResponseDto<Void> requestResetPasswordEmail(@Valid SendResetPasswordEmailRequestDto dto);
    ResponseDto<Void> verifyEmail(String token);
    boolean checkPassword(User user, String password);
    boolean checkEmail(String email);
}
