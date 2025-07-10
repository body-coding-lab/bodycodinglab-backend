package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.trainer.request.ReapplyTrainerRequestDto;
import com.bcl.fitmate.backend.dto.trainer.request.TrainerInfoRequestDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerInfoResponseDto;
import com.bcl.fitmate.backend.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TrainerServiceImpl implements TrainerService {
    @Override
    public ResponseDto<TrainerInfoResponseDto> updateTrainerInfo(Long id, TrainerInfoRequestDto dto, List<MultipartFile> files) {
        return null;
    }

    @Override
    public ResponseDto<Void> reapplyTrainer(Long id, ReapplyTrainerRequestDto dto, MultipartFile attachmentFile) {
        return null;
    }
}
