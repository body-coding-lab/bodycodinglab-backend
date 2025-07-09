package com.bcl.fitmate.backend.controller;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.config.security.UserPrincipal;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.auth.request.ReapplyTrainerRequestDto;
import com.bcl.fitmate.backend.dto.trainer.request.TrainerInfoRequestDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerInfoResponseDto;
import com.bcl.fitmate.backend.service.TrainerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiMappingPattern.TRAINER_API)
public class TrainerController {
    private final TrainerService trainerService;

    private static final String UPDATE_TRAINER_INFO = "/me";
    private static final String TRAINER_REAPPLY = "/me/reapply";

    @PreAuthorize("hasRole('TRAINER')")
    @PutMapping(UPDATE_TRAINER_INFO)
    public ResponseEntity<ResponseDto<TrainerInfoResponseDto>> updateTrainerInfo(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @ModelAttribute TrainerInfoRequestDto dto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) throws IOException {
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, trainerService.updateTrainerInfo(id, dto, files));
    }

    @PutMapping(TRAINER_REAPPLY)
    public ResponseEntity<ResponseDto<Void>> reapplyTrainer(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestPart(value = "dto") ReapplyTrainerRequestDto dto,
            @RequestPart(value = "attachmentFile") MultipartFile attachmentFile
    ) throws IOException {
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, trainerService.reapplyTrainer(id, dto, attachmentFile));
    }
}
