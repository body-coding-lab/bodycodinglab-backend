package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.enums.trainer.TrainerStatus;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.admin.request.UpdateTrainerStatusRequestDto;
import com.bcl.fitmate.backend.dto.admin.response.GetAllTrainersResponseDto;
import com.bcl.fitmate.backend.dto.admin.response.GetTrainerDetailResponseDto;
import com.bcl.fitmate.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {
    @Override
    public ResponseDto<Page<GetAllTrainersResponseDto>> getAllTrainers(int page, int size, TrainerStatus trainerStatus) {
        return null;
    }

    @Override
    public ResponseDto<GetTrainerDetailResponseDto> getTrainerDetail(Long trainerId) {
        return null;
    }

    @Override
    public ResponseDto<GetTrainerDetailResponseDto> updateTrainerStatus(Long id, Long trainerId, UpdateTrainerStatusRequestDto dto) {
        return null;
    }
}
