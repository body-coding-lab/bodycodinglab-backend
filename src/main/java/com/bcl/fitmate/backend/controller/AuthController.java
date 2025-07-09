package com.bcl.fitmate.backend.controller;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.auth.request.*;
import com.bcl.fitmate.backend.dto.auth.response.*;
import com.bcl.fitmate.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiMappingPattern.AUTH_API)
public class AuthController {
    private final AuthService authService;

    private static final String SIGNUP_MEMBER = "/signup/member";
    private static final String SIGNUP_TRAINER = "/signup/trainer";
    private static final String LOGIN = "/login";
    private static final String USERNAME_RECOVERY = "/username/recovery";
    private static final String PASSWORD_RESET_USER = "/password/reset-user";
    private static final String PASSWORD_RESET = "/password/reset";
    private static final String PASSWORD_RESET_EMAIL = "/password/reset-email";
    private static final String EMAIL_VERIFY = "/email/verify";

    @PostMapping(SIGNUP_MEMBER)
    public ResponseEntity<ResponseDto<SignUpMemberResponseDto>> signUpMember(
            @Valid @RequestPart(value = "dto") SignUpMemberRequestDto dto,
            @RequestPart(value = "profile", required = false) MultipartFile profileImage
    ) throws IOException {
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, authService.signUpMember(dto, profileImage));
    }

    @PostMapping(SIGNUP_TRAINER)
    public ResponseEntity<ResponseDto<SignUpTrainerResponseDto>> signUpTrainer(
            @Valid @RequestPart(value = "dto") SignUpTrainerRequestDto dto,
            @RequestPart(value = "attachmentFile") MultipartFile attachmentFile,
            @RequestPart(value = "profile", required = false) MultipartFile profileImage
    ) throws IOException {
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, authService.signUpTrainer(dto, attachmentFile, profileImage));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<ResponseDto<? extends LoginUserResponseDto>> login(@Valid @RequestBody LoginUserRequestDto dto) throws IOException {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping(USERNAME_RECOVERY)
    public ResponseEntity<ResponseDto<RecoverUsernameResponseDto>> recoverUsername(@Valid @RequestBody RecoverUsernameRequestDto dto) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, authService.recoverUsername(dto));
    }

    @PostMapping(PASSWORD_RESET_USER)
    public ResponseEntity<ResponseDto<GetResetPasswordUserResponseDto>> getResetPasswordUser(@Valid @RequestBody GetResetPasswordUserRequestDto dto) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, authService.getResetPasswordUser(dto));
    }

    @PostMapping(PASSWORD_RESET)
    public ResponseEntity<ResponseDto<Void>> resetPassword(
            @RequestParam String token,
            @Valid @RequestBody ResetPasswordRequestDto dto
    ) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, authService.resetPassword(token, dto));
    }

    @PostMapping(PASSWORD_RESET_EMAIL)
    public ResponseEntity<ResponseDto<Void>> requestResetPasswordEmail(@Valid @RequestBody SendResetPasswordEmailRequestDto dto) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, authService.requestResetPasswordEmail(dto));
    }

    @GetMapping(EMAIL_VERIFY)
    public ResponseEntity<ResponseDto<Void>> verifyEmail(@RequestParam String token) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, authService.verifyEmail(token));
    }
}
