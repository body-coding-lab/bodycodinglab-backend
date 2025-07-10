package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.trainer.request.TrainerCareerRequestDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerCareerResponseDto;
import com.bcl.fitmate.backend.service.TrainerCareerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TrainerCareerServiceImpl implements TrainerCareerService {
    @Override
    public ResponseDto<TrainerCareerResponseDto> postTrainerCareer(Long id, TrainerCareerRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<TrainerCareerResponseDto> updateTrainerCareer(Long id, Long careerId, TrainerCareerRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<TrainerCareerResponseDto> deleteTrainerCareer(Long id, Long careerId) {
        return null;
    }

    @Override
    public ResponseDto<Void> deleteAllTrainerCareer(Long id) {
        return null;
    }

    @Override
    public ResponseDto<List<TrainerCareerResponseDto>> getAllTrainerCareer(Long id) {
        return null;
    }

    @Override
    public ResponseDto<TrainerCareerResponseDto> getRecentTrainerCareer(Long id) {
        return null;
    }
}
