package com.bcl.fitmate.backend.controller;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.common.enums.trainer.TrainerStatus;
import com.bcl.fitmate.backend.config.security.UserPrincipal;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.admin.request.UpdateTrainerStatusRequestDto;
import com.bcl.fitmate.backend.dto.admin.response.GetAllTrainersResponseDto;
import com.bcl.fitmate.backend.dto.admin.response.GetTrainerDetailResponseDto;
import com.bcl.fitmate.backend.service.AdminService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiMappingPattern.ADMIN_API)
public class AdminController {
    private final AdminService adminService;

    private static final String ALL_TRAINERS = "/trainers";
    private static final String TRAINER_DETAIL = ALL_TRAINERS + "/{trainerId}";
    private static final String TRAINER_STATUS = TRAINER_DETAIL + "/status";

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(ALL_TRAINERS)
    public ResponseEntity<ResponseDto<Page<GetAllTrainersResponseDto>>> getAllTrainers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false)TrainerStatus trainerStatus
    ) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, adminService.getAllTrainers(page, size, trainerStatus));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(TRAINER_DETAIL)
    public ResponseEntity<ResponseDto<GetTrainerDetailResponseDto>> getTrainerDetail(@PathVariable Long trainerId) {
        return ResponseDto.toResponseEntity(HttpStatus.OK, adminService.getTrainerDetail(trainerId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(TRAINER_STATUS)
    public ResponseEntity<ResponseDto<Void>> updateTrainerStatus(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long trainerId,
            @Valid @RequestBody UpdateTrainerStatusRequestDto dto
    ) throws MessagingException {
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, adminService.updateTrainerStatus(id, trainerId, dto));
    }
}
