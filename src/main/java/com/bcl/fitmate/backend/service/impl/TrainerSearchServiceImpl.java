package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerCareerResponseDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerDetailResponseDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerLicenseResponseDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerListResponseDto;
import com.bcl.fitmate.backend.service.TrainerSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TrainerSearchServiceImpl implements TrainerSearchService {
    @Override
    public ResponseDto<List<TrainerCareerResponseDto>> getTrainerCareer(Long trainerId) {
        return null;
    }

    @Override
    public ResponseDto<List<TrainerLicenseResponseDto>> getTrainerLicense(Long trainerId) {
        return null;
    }

    @Override
    public ResponseDto<List<TrainerListResponseDto>> getAllTrainers() {
        return null;
    }

    @Override
    public ResponseDto<TrainerDetailResponseDto> getTrainerById(Long trainerId) {
        return null;
    }

    @Override
    public ResponseDto<List<TrainerListResponseDto>> searchTrainerByName(String name) {
        return null;
    }

    @Override
    public ResponseDto<List<TrainerListResponseDto>> searchTrainerByAddress(String jobAddress) {
        return null;
    }
}
