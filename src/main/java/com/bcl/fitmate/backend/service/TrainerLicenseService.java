package com.bcl.fitmate.backend.service;

import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.trainer.request.TrainerLicenseRequestDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerLicenseDetailResponseDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerLicenseResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TrainerLicenseService {
    ResponseDto<TrainerLicenseResponseDto> postTrainerLicense(Long id, TrainerLicenseRequestDto dto, List<MultipartFile> files);

    ResponseDto<TrainerLicenseResponseDto> updateTrainerLicense(Long id, Long licenseId, TrainerLicenseRequestDto dto, List<MultipartFile> files);

    ResponseDto<TrainerLicenseResponseDto> deleteTrainerLicense(Long id, Long licenseId);

    ResponseDto<Void> deleteAllTrainerLicense(Long id);

    ResponseDto<List<TrainerLicenseDetailResponseDto>> getAllTrainerLicense(Long id);

    ResponseDto<TrainerLicenseResponseDto> getRecentTrainerLicense(Long id);
}
