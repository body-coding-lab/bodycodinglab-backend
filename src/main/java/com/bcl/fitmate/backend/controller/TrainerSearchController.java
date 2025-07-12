package com.bcl.fitmate.backend.controller;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.trainer.response.*;
import com.bcl.fitmate.backend.service.TrainerSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiMappingPattern.COMMON_API)
public class TrainerSearchController {
    private final TrainerSearchService trainerSearchService;

    private static final String GET_TRAINER_CAREER = "/careers";
    private static final String GET_TRAINER_LICENSE = "/licenses";
    private static final String GET_ALL_TRAINER_INFO = "/trainers";
    private static final String GET_TRAINER_INFO = "/{trainerId}";
    private static final String GET_TRAINER_BY_NAME = "/search-name";
    private static final String GET_TRAINER_BY_ADDRESS = "/search-address";

    @GetMapping(GET_TRAINER_CAREER)
    public ResponseEntity<ResponseDto<List<TrainerCareerResponseDto>>> getTrainerCareer(
            @RequestParam Long trainerId
    ) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, trainerSearchService.getTrainerCareer(trainerId));
    }

    @GetMapping(GET_TRAINER_LICENSE)
    public ResponseEntity<ResponseDto<List<TrainerLicenseDetailResponseDto>>> getTrainerLicense(
            @RequestParam Long trainerId
    ) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, trainerSearchService.getTrainerLicense(trainerId));
    }

    @GetMapping(GET_ALL_TRAINER_INFO)
    public ResponseEntity<ResponseDto<List<TrainerListResponseDto>>> getAllTrainers() {
        return ResponseDto.toResponseEntity(HttpStatus.OK, trainerSearchService.getAllTrainers());
    }

    @GetMapping(GET_TRAINER_INFO)
    public ResponseEntity<ResponseDto<TrainerDetailResponseDto>> getTrainerById(
            @PathVariable Long trainerId
    ) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, trainerSearchService.getTrainerById(trainerId));
    }

    @GetMapping(GET_TRAINER_BY_NAME)
    public ResponseEntity<ResponseDto<List<TrainerListResponseDto>>> searchTrainerByName(
            @RequestParam String name
    ) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, trainerSearchService.searchTrainerByName(name));
    }

    @GetMapping(GET_TRAINER_BY_ADDRESS)
    public ResponseEntity<ResponseDto<List<TrainerListResponseDto>>> searchTrainerByAddress(
            @RequestParam String jobAddress
    ) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, trainerSearchService.searchTrainerByAddress(jobAddress));
    }
}
