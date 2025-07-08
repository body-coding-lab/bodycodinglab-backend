package com.bcl.fitmate.backend.controller;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.trainer.request.TrainerCareerRequestDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerCareerResponseDto;
import com.bcl.fitmate.backend.service.TrainerCareerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiMappingPattern.TRAINER_API)
public class TrainerCareerController {
    private final TrainerCareerService trainerCareerService;

    private static final String POST_TRAINER_CAREER = "/me/careers";
    private static final String UPDATE_TRAINER_CAREER = "/me/careers/{careerId}";
    private static final String DELETE_TRAINER_CAREER = "/me/careers/{careerId}";
    private static final String DELETE_ALL_TRAINER_CAREER = "/me/careers";
    private static final String GET_ALL_TRAINER_CAREER = "/me/careers";
    private static final String GET_RECENT_TRAINER_CAREER = "/me/careers/recent";

    @PreAuthorize("hasRole('TRAINER')")
    @PostMapping(POST_TRAINER_CAREER)
    public ResponseEntity<ResponseDto<TrainerCareerResponseDto>> postTrainerCareer(
            @AuthenticationPrincipal Long id,
            @Valid @RequestBody TrainerCareerRequestDto dto
    ) {
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, trainerCareerService.postTrainerCareer(id, dto));
    }

    @PreAuthorize("hasRole('TRAINER')")
    @PutMapping(UPDATE_TRAINER_CAREER)
    public ResponseEntity<ResponseDto<TrainerCareerResponseDto>> updateTrainerCareer(
            @AuthenticationPrincipal Long id,
            @PathVariable Long careerId,
            @Valid @RequestBody TrainerCareerRequestDto dto
    ) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, trainerCareerService.updateTrainerCareer(id, careerId, dto));
    }

    @PreAuthorize("hasRole('TRAINER')")
    @DeleteMapping(DELETE_TRAINER_CAREER)
    public ResponseEntity<ResponseDto<TrainerCareerResponseDto>> deleteTrainerCareer(
            @AuthenticationPrincipal Long id,
            @PathVariable Long careerId
    ) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, trainerCareerService.deleteTrainerCareer(id, careerId));
    }

    @PreAuthorize("hasRole('TRAINER')")
    @DeleteMapping(DELETE_ALL_TRAINER_CAREER)
    public ResponseEntity<ResponseDto<Void>> deleteAllTrainerCareer(
            @AuthenticationPrincipal Long id
    ) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, trainerCareerService.deleteAllTrainerCareer(id));
    }

    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping(GET_ALL_TRAINER_CAREER)
    public ResponseEntity<ResponseDto<List<TrainerCareerResponseDto>>> getAllTrainerCareer(
            @AuthenticationPrincipal Long id
    ) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, trainerCareerService.getAllTrainerCareer(id));
    }

    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping(GET_RECENT_TRAINER_CAREER)
    public ResponseEntity<ResponseDto<TrainerCareerResponseDto>> getRecentTrainerCareer(
            @AuthenticationPrincipal Long id
    ) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, trainerCareerService.getRecentTrainerCareer(id));
    }
}
