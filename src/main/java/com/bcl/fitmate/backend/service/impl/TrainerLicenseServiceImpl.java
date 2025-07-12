package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.common.enums.uploadFile.TargetType;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.trainer.request.TrainerLicenseRequestDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerCareerResponseDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerLicenseDetailResponseDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerLicenseResponseDto;
import com.bcl.fitmate.backend.dto.uploadFile.response.FileResponseDto;
import com.bcl.fitmate.backend.entity.*;
import com.bcl.fitmate.backend.repository.*;
import com.bcl.fitmate.backend.service.TrainerLicenseService;
import com.bcl.fitmate.backend.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TrainerLicenseServiceImpl implements TrainerLicenseService {
    private final TrainerLicenseRepository trainerLicenseRepository;
    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final UploadFileService uploadFileService;
    private final UploadFileRepository uploadFileRepository;

    @Override
    public ResponseDto<TrainerLicenseResponseDto> postTrainerLicense(Long id, TrainerLicenseRequestDto dto, List<MultipartFile> files) {
        TrainerLicenseResponseDto data = null;

        User user = userRepository.findById(id)
                .orElse(null);

        if(user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        Trainer trainer = trainerRepository.findById(user.getTrainer().getId())
                .orElse(null);

        if(trainer == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        TrainerLicense license = TrainerLicense.create(trainer,
                dto.getLicenseType(),
                dto.getLicenseName());

        TrainerLicense savedLicense = trainerLicenseRepository.save(license);

        List<String> fileNames = new ArrayList<>();

        if(files != null && !files.isEmpty()) {
            List<MultipartFile> nonEmptyFiles = files.stream()
                    .filter(file -> !file.isEmpty())
                    .collect(Collectors.toList());

            if (!nonEmptyFiles.isEmpty()) {
                List<FileResponseDto> uploadFiles = uploadFileService.uploadMultiFiles(nonEmptyFiles, user.getTrainer().getId(), TargetType.LICENSE);

                fileNames = uploadFiles.stream()
                        .map(FileResponseDto::getFileName)
                        .collect(Collectors.toList());
            }
        }


        data = TrainerLicenseResponseDto.builder()
                .id(savedLicense.getId())
                .trainerId(savedLicense.getTrainer().getId())
                .licenseType(savedLicense.getLicenseType())
                .licenseName(savedLicense.getLicenseName())
                .fileNames(fileNames)
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<TrainerLicenseResponseDto> updateTrainerLicense(Long id, Long licenseId, TrainerLicenseRequestDto dto, List<MultipartFile> files) {
        TrainerLicenseResponseDto data = null;

        User user = userRepository.findById(id)
                .orElse(null);

        if(user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        Trainer trainer = trainerRepository.findById(user.getTrainer().getId())
                .orElse(null);

        if(trainer == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        TrainerLicense license = trainerLicenseRepository.findById(licenseId)
                .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.NOT_EXISTS_LICENSE));

        license.setLicenseType(dto.getLicenseType());
        license.setLicenseName(dto.getLicenseName());

        TrainerLicense updatedLicense = trainerLicenseRepository.save(license);

        List<String> fileNames = new ArrayList<>();

        if(files != null && !files.isEmpty()) {
            List<MultipartFile> nonEmptyFiles = files.stream()
                    .filter(file -> !file.isEmpty())
                    .collect(Collectors.toList());

            if (!nonEmptyFiles.isEmpty()) {
                List<FileResponseDto> uploadFiles = uploadFileService.uploadMultiFiles(nonEmptyFiles, user.getTrainer().getId(), TargetType.LICENSE);

                fileNames = uploadFiles.stream()
                        .map(FileResponseDto::getFileName)
                        .collect(Collectors.toList());
            }
        }

        data = TrainerLicenseResponseDto.builder()
                .id(updatedLicense.getId())
                .trainerId(updatedLicense.getTrainer().getId())
                .licenseType(updatedLicense.getLicenseType())
                .licenseName(updatedLicense.getLicenseName())
                .fileNames(fileNames)
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<TrainerLicenseResponseDto> deleteTrainerLicense(Long id, Long licenseId) {
        TrainerLicenseResponseDto data = null;

        User user = userRepository.findById(id)
                .orElse(null);

        if(user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        Trainer trainer = trainerRepository.findById(user.getTrainer().getId())
                .orElse(null);

        if(trainer == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        TrainerLicense license = trainerLicenseRepository.findById(licenseId)
                .orElseThrow(() -> new IllegalStateException(ResponseMessage.NOT_EXISTS_CAREER));

        trainerLicenseRepository.delete(license);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<Void> deleteAllTrainerLicense(Long id) {
        User user = userRepository.findById(id)
                .orElse(null);

        if(user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        Trainer trainer = trainerRepository.findById(user.getTrainer().getId())
                .orElse(null);

        if(trainer == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        List<TrainerLicense> licenses = trainerLicenseRepository.findByTrainerId(trainer.getId())
                .orElseThrow(() -> new IllegalStateException(ResponseMessage.NOT_EXISTS_CAREER));

        trainerLicenseRepository.deleteAll(licenses);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, null);

    }

    @Override
    public ResponseDto<List<TrainerLicenseDetailResponseDto>> getAllTrainerLicense(Long id) {
        User user = userRepository.findById(id)
                .orElse(null);

        if(user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        Trainer trainer = trainerRepository.findById(user.getTrainer().getId())
                .orElse(null);

        if(trainer == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        List<TrainerLicense> licenses = trainerLicenseRepository.findByTrainerId(trainer.getId())
                .orElseThrow(() -> new IllegalStateException(ResponseMessage.NOT_EXISTS_CAREER));

        List<TrainerLicenseDetailResponseDto> data = licenses.stream()
                .map(license -> {
                    List<UploadFile> licenseFiles = uploadFileRepository.findAllByTargetIdAndTargetType(
                            trainer.getId(), TargetType.LICENSE);

                    List<FileResponseDto> licenseImageDtos = licenseFiles.stream()
                            .map(FileResponseDto::fromEntity)
                            .collect(Collectors.toList());

                    return TrainerLicenseDetailResponseDto.builder()
                            .id(license.getId())
                            .trainerId(license.getTrainer().getId())
                            .licenseType(license.getLicenseType())
                            .licenseName(license.getLicenseName())
                            .licenseImage(licenseImageDtos)
                            .build();
                        })
                .collect(Collectors.toList());
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);

    }

    @Override
    public ResponseDto<TrainerLicenseResponseDto> getRecentTrainerLicense(Long id) {
        TrainerLicenseResponseDto data = null;

        User user = userRepository.findById(id)
                .orElse(null);

        if(user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        Trainer trainer = trainerRepository.findById(user.getTrainer().getId())
                .orElse(null);

        if(trainer == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        TrainerLicense license = trainerLicenseRepository.findTopByTrainerIdOrderByIdDesc(trainer.getId());

        data = TrainerLicenseResponseDto.builder()
                .id(license.getId())
                .trainerId(license.getTrainer().getId())
                .licenseType(license.getLicenseType())
                .licenseName(license.getLicenseName())
                .fileNames(null)
                .build();
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }
}
