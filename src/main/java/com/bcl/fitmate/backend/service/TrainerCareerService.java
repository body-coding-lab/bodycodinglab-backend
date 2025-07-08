package com.bcl.fitmate.backend.service;

import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.trainer.request.TrainerCareerRequestDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerCareerResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface TrainerCareerService {
    ResponseDto<TrainerCareerResponseDto> postTrainerCareer(Long id, @Valid TrainerCareerRequestDto dto);

    ResponseDto<TrainerCareerResponseDto> updateTrainerCareer(Long id, Long careerId, @Valid TrainerCareerRequestDto dto);

    ResponseDto<TrainerCareerResponseDto> deleteTrainerCareer(Long id, Long careerId);

    ResponseDto<Void> deleteAllTrainerCareer(Long id);

    ResponseDto<List<TrainerCareerResponseDto>> getAllTrainerCareer(Long id);

    ResponseDto<TrainerCareerResponseDto> getRecentTrainerCareer(Long id);
}
