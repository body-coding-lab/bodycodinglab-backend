package com.bcl.fitmate.backend.controller;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.trainer.request.TrainerCareerRequestDto;
import com.bcl.fitmate.backend.dto.trainer.request.TrainerLicenseRequestDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerLicenseResponseDto;
import com.bcl.fitmate.backend.service.TrainerLicenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiMappingPattern.TRAINER_API)
public class TrainerLicenseController {
    private final TrainerLicenseService trainerLicenseService;

    private static final String POST_TRAINER_LICENSE = "/me/licenses";
    private static final String UPDATE_TRAINER_LICENSE = "/me/licenses/{licenseId}";
    private static final String DELETE_TRAINER_LICENSE = "/me/licenses/{licenseId}";
    private static final String DELETE_ALL_TRAINER_LICENSE = "/me/licenses";
    private static final String GET_ALL_TRAINER_LICENSE = "/me/licenses";
    private static final String GET_RECENT_TRAINER_LICENSE = "/me/licenses/recent";

    @PreAuthorize("hasRole('TRAINER')")
    @PostMapping(POST_TRAINER_LICENSE)
    public ResponseEntity<ResponseDto<TrainerLicenseResponseDto>> postTrainerLicense(
            @AuthenticationPrincipal Long id,
            @ModelAttribute TrainerLicenseRequestDto dto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, trainerLicenseService.postTrainerLicense(id, dto, files));
    }

    @PreAuthorize("hasRole('TRAINER')")
    @PutMapping(UPDATE_TRAINER_LICENSE)
    public ResponseEntity<ResponseDto<TrainerLicenseResponseDto>> updateTrainerLicense(
            @AuthenticationPrincipal Long id,
            @PathVariable Long licenseId,
            @ModelAttribute TrainerLicenseRequestDto dto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, trainerLicenseService.updateTrainerLicense(id, licenseId, dto, files));
    }

    @PreAuthorize("hasRole('TRAINER')")
    @DeleteMapping(DELETE_TRAINER_LICENSE)
    public ResponseEntity<ResponseDto<TrainerLicenseResponseDto>> deleteTrainerLicense(
            @AuthenticationPrincipal Long id,
            @PathVariable Long licenseId
    ) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, trainerLicenseService.deleteTrainerLicense(id, licenseId));
    }

    @PreAuthorize("hasRole('TRAINER')")
    @DeleteMapping(DELETE_ALL_TRAINER_LICENSE)
    public ResponseEntity<ResponseDto<Void>> deleteAllTrainerLicense(
            @AuthenticationPrincipal Long id
    ) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, trainerLicenseService.deleteAllTrainerLicense(id));
    }

    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping(GET_ALL_TRAINER_LICENSE)
    public ResponseEntity<ResponseDto<List<TrainerLicenseResponseDto>>> getAllTrainerLicense(
            @AuthenticationPrincipal Long id
    ) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, trainerLicenseService.getAllTrainerLicense(id));
    }

    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping(GET_RECENT_TRAINER_LICENSE)
    public ResponseEntity<ResponseDto<TrainerLicenseResponseDto>> getRecentTrainerLicense(
            @AuthenticationPrincipal Long id
    ) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, trainerLicenseService.getRecentTrainerLicense(id));
    }
}
