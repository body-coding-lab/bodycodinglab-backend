package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.trainer.request.TrainerLicenseRequestDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerLicenseResponseDto;
import com.bcl.fitmate.backend.service.TrainerLicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TrainerLicenseServiceImpl implements TrainerLicenseService {
    @Override
    public ResponseDto<TrainerLicenseResponseDto> postTrainerLicense(Long id, TrainerLicenseRequestDto dto, List<MultipartFile> files) {
        return null;
    }

    @Override
    public ResponseDto<TrainerLicenseResponseDto> updateTrainerLicense(Long id, Long licenseId, TrainerLicenseRequestDto dto, List<MultipartFile> files) {
        return null;
    }

    @Override
    public ResponseDto<TrainerLicenseResponseDto> deleteTrainerLicense(Long id, Long licenseId) {
        return null;
    }

    @Override
    public ResponseDto<Void> deleteAllTrainerLicense(Long id) {
        return null;
    }

    @Override
    public ResponseDto<List<TrainerLicenseResponseDto>> getAllTrainerLicense(Long id) {
        return null;
    }

    @Override
    public ResponseDto<TrainerLicenseResponseDto> getRecentTrainerLicense(Long id) {
        return null;
    }
}
