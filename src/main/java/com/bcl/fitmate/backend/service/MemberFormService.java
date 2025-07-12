package com.bcl.fitmate.backend.service;

import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.memberForm.request.CreateMemberFormRequestDto;
import com.bcl.fitmate.backend.dto.memberForm.response.CreateMemberFormResponseDto;
import com.bcl.fitmate.backend.dto.memberForm.response.GetMemberFormResponseDto;
import jakarta.validation.Valid;



public interface MemberFormService {
    ResponseDto<CreateMemberFormResponseDto> createMemberForm(Long userId, @Valid CreateMemberFormRequestDto dto);

    ResponseDto<GetMemberFormResponseDto> getMemberForm(Long userId);
}
