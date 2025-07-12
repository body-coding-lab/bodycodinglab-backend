package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.common.enums.uploadFile.TargetType;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.trainer.response.*;
import com.bcl.fitmate.backend.dto.uploadFile.response.FileResponseDto;
import com.bcl.fitmate.backend.entity.Trainer;
import com.bcl.fitmate.backend.entity.TrainerCareer;
import com.bcl.fitmate.backend.entity.TrainerLicense;
import com.bcl.fitmate.backend.entity.UploadFile;
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
    public ResponseDto<List<TrainerLicenseDetailResponseDto>> getTrainerLicense(Long trainerId) {
        List<TrainerLicenseDetailResponseDto> data = null;

        List<TrainerLicense> licenses = trainerLicenseRepository.findByTrainerId(trainerId)
                .orElseThrow(() -> new IllegalStateException(ResponseMessage.NOT_EXISTS_CAREER));

        List<UploadFile> files = uploadFileRepository.findAllByTargetIdAndTargetType(
                trainerId, TargetType.LICENSE
        );

        List<FileResponseDto> licenseImageDtos = files.stream()
                .map(FileResponseDto::fromEntity)
                .collect(Collectors.toList());

        data = licenses.stream()
                .map(license -> TrainerLicenseDetailResponseDto.builder()
                        .trainerId(license.getTrainer().getId())
                        .licenseType(license.getLicenseType())
                        .licenseName(license.getLicenseName())
                        .licenseImage(licenseImageDtos)
                        .build())
                .collect(Collectors.toList());
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);

    }

    @Override
    public ResponseDto<List<TrainerListResponseDto>> getAllTrainers() {
        List<TrainerListResponseDto> data = null;

        List<Trainer> trainers = trainerRepository.findAllWithUserAndProfileImage();

        data = trainers.stream()
                .map(trainer -> TrainerListResponseDto.builder()
                        .trainerId(trainer.getId())
                        .name(trainer.getUser().getName())
                        .shortIntroduce(trainer.getShortIntroduce())
                        .jobAddress(trainer.getJobAddress())
                        .profileImage(
                                trainer.getUser().getProfileImage() != null
                                ? trainer.getUser().getProfileImage().getFullUrl()
                                        : null
                        )
                        .build())
                .collect(Collectors.toList());
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<TrainerDetailResponseDto> getTrainerById(Long trainerId) {
        TrainerDetailResponseDto data = null;

        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.TRAINER_NOT_FOUND));

        List<TrainerCareerResponseDto> careers = trainer.getTrainerCareers().stream()
                .map(career -> TrainerCareerResponseDto.builder()
                        .trainerId(career.getTrainer().getId())
                        .companyName(career.getCompanyName())
                        .companyJoin(career.getCompanyJoin())
                        .companyQuit(career.getCompanyQuit())
                        .build())
                .collect(Collectors.toList());

        List<UploadFile> files = uploadFileRepository.findAllByTargetIdAndTargetType(
                trainerId, TargetType.LICENSE
        );

        List<FileResponseDto> licenseImageDtos = files.stream()
                .map(FileResponseDto::fromEntity)
                .collect(Collectors.toList());

        List<TrainerLicenseDetailResponseDto> licenses = trainer.getTrainerLicenses().stream()
                .map(license -> TrainerLicenseDetailResponseDto.builder()
                        .trainerId(license.getTrainer().getId())
                        .licenseType(license.getLicenseType())
                        .licenseName(license.getLicenseName())
                        .licenseImage(licenseImageDtos)
                        .build())
                .collect(Collectors.toList());

        String profileUrl = null;
        if(trainer.getUser().getProfileImage() != null) {
            UploadFile image = trainer.getUser().getProfileImage();
            profileUrl = "/files/" + image.getFileName();
        }

        List<UploadFile> infoFiles = uploadFileRepository.findAllByTargetIdAndTargetType(
                trainerId, TargetType.INFO
        );

        List<FileResponseDto> infoImageDtos = files.stream()
                .map(FileResponseDto::fromEntity)
                .collect(Collectors.toList());

        data = TrainerDetailResponseDto.builder()
                .trainerId(trainer.getId())
                .name(trainer.getUser().getName())
                .jobAddress(trainer.getJobAddress())
                .shortIntroduce(trainer.getShortIntroduce())
                .longIntroduce(trainer.getLongIntroduce())
                .educationName(trainer.getEducationName())
                .educationEntrance(trainer.getEducationEntrance())
                .educationGraduate(trainer.getEducationGraduate())
                .careers(careers)
                .licenses(licenses)
                .profileImage(profileUrl)
                .infoImages(infoImageDtos)
                .licenseImages(licenseImageDtos)
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<List<TrainerListResponseDto>> searchTrainerByName(String name) {
        List<TrainerListResponseDto> data = null;

        List<Trainer> trainers = trainerRepository.findAllWithUserAndProfileImage();

        data = trainers.stream()
                .map(trainer -> TrainerListResponseDto.builder()
                        .trainerId(trainer.getId())
                        .name(trainer.getUser().getName())
                        .shortIntroduce(trainer.getShortIntroduce())
                        .jobAddress(trainer.getJobAddress())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<List<TrainerListResponseDto>> searchTrainerByAddress(String jobAddress) {
        List<TrainerListResponseDto> data = null;

        List<Trainer> trainers = trainerRepository.findAllWithUserAndProfileImage();

        data = trainers.stream()
                .map(trainer -> TrainerListResponseDto.builder()
                        .trainerId(trainer.getId())
                        .name(trainer.getUser().getName())
                        .shortIntroduce(trainer.getShortIntroduce())
                        .jobAddress(trainer.getJobAddress())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }
}
