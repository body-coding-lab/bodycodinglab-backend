package com.bcl.fitmate.backend.service;

import com.bcl.fitmate.backend.common.enums.trainer.TrainerStatus;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.admin.request.UpdateTrainerStatusRequestDto;
import com.bcl.fitmate.backend.dto.admin.response.GetAllTrainersResponseDto;
import com.bcl.fitmate.backend.dto.admin.response.GetTrainerDetailResponseDto;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface AdminService {
    ResponseDto<Page<GetAllTrainersResponseDto>> getAllTrainers(int page, int size, TrainerStatus trainerStatus);
    ResponseDto<GetTrainerDetailResponseDto> getTrainerDetail(Long trainerId);
    ResponseDto<Void> updateTrainerStatus(Long id, Long trainerId, @Valid UpdateTrainerStatusRequestDto dto) throws MessagingException;
}
