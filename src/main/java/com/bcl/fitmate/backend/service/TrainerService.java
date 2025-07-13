package com.bcl.fitmate.backend.service;

import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.trainer.request.ReapplyTrainerRequestDto;
import com.bcl.fitmate.backend.dto.trainer.request.TrainerInfoRequestDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerInfoResponseDto;
import com.bcl.fitmate.backend.entity.Trainer;
import com.bcl.fitmate.backend.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TrainerService {
    ResponseDto<TrainerInfoResponseDto> updateTrainerInfo(Long id, TrainerInfoRequestDto dto, List<MultipartFile> files);
    ResponseDto<Void> reapplyTrainer(Long id, ReapplyTrainerRequestDto dto, MultipartFile attachmentFile);
    Trainer getTrainerById(Long id);
}
