package com.bcl.fitmate.backend.controller;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.config.security.UserPrincipal;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.user.request.DeleteUserRequestDto;
import com.bcl.fitmate.backend.dto.user.request.UpdateMemberInfoRequestDto;
import com.bcl.fitmate.backend.dto.user.request.UpdateTrainerInfoRequestDto;
import com.bcl.fitmate.backend.dto.user.response.GetMemberInfoResponseDto;
import com.bcl.fitmate.backend.dto.user.response.GetTrainerInfoResponseDto;
import com.bcl.fitmate.backend.dto.user.response.GetUserInfoResponseDto;
import com.bcl.fitmate.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiMappingPattern.USER_API)
public class UserController {
    private final UserService userService;

    private static final String MY_INFO = "/me";
    private static final String MEMBER_MY_INFO = "/members/me";
    private static final String TRAINER_MY_INFO = "/trainers/me";
    private static final String PROFILE_IMAGE = MY_INFO + "/profile-image";

    @GetMapping(MY_INFO)
    public ResponseEntity<ResponseDto<GetUserInfoResponseDto>> getUserInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, userService.getUserInfo(id));
    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping(MEMBER_MY_INFO)
    public ResponseEntity<ResponseDto<GetMemberInfoResponseDto>> getMemberInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, userService.getMemberInfo(id));
    }

    @PreAuthorize("hasRole('MEMBER')")
    @PutMapping(MEMBER_MY_INFO)
    public ResponseEntity<ResponseDto<Void>> updateMemberInfo(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody UpdateMemberInfoRequestDto dto
    ) {
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, userService.updateMemberInfo(id, dto));
    }

    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping(TRAINER_MY_INFO)
    public ResponseEntity<ResponseDto<GetTrainerInfoResponseDto>> getTrainerInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, userService.getTrainerInfo(id));
    }

    @PreAuthorize("hasRole('TRAINER')")
    @PutMapping(TRAINER_MY_INFO)
    public ResponseEntity<ResponseDto<Void>> updateTrainerInfo(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody UpdateTrainerInfoRequestDto dto
    ) {
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, userService.updateTrainerInfo(id, dto));
    }

    @PreAuthorize("hasAnyRole('MEMBER', 'TRAINER')")
    @DeleteMapping(MY_INFO)
    public ResponseEntity<ResponseDto<Void>> deleteUser(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody DeleteUserRequestDto dto
    ) {
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, userService.deleteUser(id, dto));
    }

    @PreAuthorize("hasAnyRole('MEMBER', 'TRAINER')")
    @PutMapping(PROFILE_IMAGE)
    public ResponseEntity<ResponseDto<Void>> updateProfileImage(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestPart(value = "profile") MultipartFile profileImage
    ) throws IOException {
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, userService.updateProfileImage(id, profileImage));
    }

    @PreAuthorize("hasAnyRole('MEMBER', 'TRAINER')")
    @DeleteMapping(PROFILE_IMAGE)
    public ResponseEntity<ResponseDto<Void>> deleteProfileImage(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, userService.deleteProfileImage(id));
    }
}
