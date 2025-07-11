package com.bcl.fitmate.backend.controller;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.config.security.UserPrincipal;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.memberForm.request.CreateMemberFormRequestDto;
import com.bcl.fitmate.backend.dto.memberForm.response.CreateMemberFormResponseDto;
import com.bcl.fitmate.backend.dto.memberForm.response.GetMemberFormResponseDto;
import com.bcl.fitmate.backend.service.MemberFormService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.MEMBER_FORM_API)
@RequiredArgsConstructor
public class MemberFormController {
    private MemberFormService memberFormService;

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping
    public ResponseEntity<ResponseDto<CreateMemberFormResponseDto>> createMemberForm(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody CreateMemberFormRequestDto dto
            ){
        Long userId = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, memberFormService.createMemberForm(userId, dto));
    }


    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping
    public ResponseEntity<ResponseDto<GetMemberFormResponseDto>> getMemberForm(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ){
        Long userId = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, memberFormService.getMemberForm(userId));
    }
}
