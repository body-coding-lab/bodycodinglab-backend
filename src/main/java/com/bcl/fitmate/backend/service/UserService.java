package com.bcl.fitmate.backend.service;

import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.user.request.DeleteUserRequestDto;
import com.bcl.fitmate.backend.dto.user.request.UpdateMemberInfoRequestDto;
import com.bcl.fitmate.backend.dto.user.request.UpdateTrainerInfoRequestDto;
import com.bcl.fitmate.backend.dto.user.response.GetMemberInfoResponseDto;
import com.bcl.fitmate.backend.dto.user.response.GetTrainerInfoResponseDto;
import com.bcl.fitmate.backend.dto.user.response.GetUserInfoResponseDto;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    ResponseDto<GetUserInfoResponseDto> getUserInfo(Long id);
    ResponseDto<GetMemberInfoResponseDto> getMemberInfo(Long id);
    ResponseDto<GetMemberInfoResponseDto> updateMemberInfo(Long id, @Valid UpdateMemberInfoRequestDto dto);
    ResponseDto<GetTrainerInfoResponseDto> getTrainerInfo(Long id);
    ResponseDto<GetTrainerInfoResponseDto> updateTrainerInfo(Long id, @Valid UpdateTrainerInfoRequestDto dto);
    ResponseDto<Void> deleteUser(Long id, @Valid DeleteUserRequestDto dto);
    ResponseDto<Void> updateProfileImage(Long id, MultipartFile profileImage);
    ResponseDto<Void> deleteProfileImage(Long id);
}
