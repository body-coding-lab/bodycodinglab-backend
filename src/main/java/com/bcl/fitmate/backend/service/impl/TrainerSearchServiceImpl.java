package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerCareerResponseDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerDetailResponseDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerLicenseResponseDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerListResponseDto;
import com.bcl.fitmate.backend.entity.TrainerCareer;
import com.bcl.fitmate.backend.repository.TrainerCareerRepository;
import com.bcl.fitmate.backend.repository.TrainerLicenseRepository;
import com.bcl.fitmate.backend.repository.TrainerRepository;
import com.bcl.fitmate.backend.repository.UploadFileRepository;
import com.bcl.fitmate.backend.service.TrainerSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TrainerSearchServiceImpl implements TrainerSearchService {
    private final TrainerCareerRepository trainerCareerRepository;
    private final TrainerLicenseRepository trainerLicenseRepository;
    private final TrainerRepository trainerRepository;
    private final UploadFileRepository uploadFileRepository;

    @Override
    public ResponseDto<List<TrainerCareerResponseDto>> getTrainerCareer(Long trainerId) {
        List<TrainerCareerResponseDto> data = null;

        List<TrainerCareer> careers = trainerCareerRepository.findByTrainerId(trainerId)
                .orElseThrow(() -> new IllegalStateException(ResponseMessage.NOT_EXISTS_CAREER));

        data = careers.stream()
                .map(career -> TrainerCareerResponseDto.builder()
                        .trainerId(career.getTrainer().getId())
                        .companyName(career.getCompanyName())
                        .companyJoin(career.getCompanyJoin())
                        .companyQuit(career.getCompanyQuit())
                        .build())
                .collect(Collectors.toList());
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);

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
