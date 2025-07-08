package com.bcl.fitmate.backend.service;

import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerCareerResponseDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerDetailResponseDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerLicenseResponseDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerListResponseDto;

import java.util.List;

public interface TrainerSearchService {
    ResponseDto<List<TrainerCareerResponseDto>> getTrainerCareer(Long trainerId);

    ResponseDto<List<TrainerLicenseResponseDto>> getTrainerLicense(Long trainerId);

    ResponseDto<List<TrainerListResponseDto>> getAllTrainers();

    ResponseDto<TrainerDetailResponseDto> getTrainerById(Long trainerId);

    ResponseDto<List<TrainerListResponseDto>> searchTrainerByName(String name);

    ResponseDto<List<TrainerListResponseDto>> searchTrainerByAddress(String jobAddress);
}
