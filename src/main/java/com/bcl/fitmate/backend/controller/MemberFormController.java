package com.bcl.fitmate.backend.controller;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
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
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody CreateMemberFormRequestDto dto
            ){
        ResponseDto<CreateMemberFormResponseDto> response = memberFormService.createMemberForm(userId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping
    public ResponseEntity<ResponseDto<GetMemberFormResponseDto>> getMemberForm(
            @AuthenticationPrincipal Long userId
    ){
        ResponseDto<GetMemberFormResponseDto> response = memberFormService.getMemberForm(userId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
