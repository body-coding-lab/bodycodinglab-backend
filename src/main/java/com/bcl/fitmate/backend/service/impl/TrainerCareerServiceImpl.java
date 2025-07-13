package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.trainer.request.TrainerCareerRequestDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerCareerResponseDto;
import com.bcl.fitmate.backend.entity.Trainer;
import com.bcl.fitmate.backend.entity.TrainerCareer;
import com.bcl.fitmate.backend.entity.User;
import com.bcl.fitmate.backend.repository.TrainerCareerRepository;
import com.bcl.fitmate.backend.service.TrainerCareerService;
import com.bcl.fitmate.backend.service.TrainerService;
import com.bcl.fitmate.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TrainerCareerServiceImpl implements TrainerCareerService {
    private final TrainerCareerRepository trainerCareerRepository;
    private final UserService userService;
    private final TrainerService trainerService;

    @Override
    public ResponseDto<TrainerCareerResponseDto> postTrainerCareer(Long id, TrainerCareerRequestDto dto) {
        TrainerCareerResponseDto data = null;

        User user = userService.getUserById(id);

        Trainer trainer = trainerService.getTrainerById(user.getTrainer().getId());

        TrainerCareer career = TrainerCareer.create(trainer,
                dto.getCompanyName(),
                dto.getCompanyJoin(),
                dto.getCompanyQuit());

        TrainerCareer savedCareer = trainerCareerRepository.save(career);

        data = TrainerCareerResponseDto.builder()
                .id(savedCareer.getId())
                .trainerId(savedCareer.getTrainer().getId())
                .companyName(savedCareer.getCompanyName())
                .companyJoin(savedCareer.getCompanyJoin())
                .companyQuit(savedCareer.getCompanyQuit())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<TrainerCareerResponseDto> updateTrainerCareer(Long id, Long careerId, TrainerCareerRequestDto dto) {
        TrainerCareerResponseDto data = null;

        User user = userService.getUserById(id);

        Trainer trainer = trainerService.getTrainerById(user.getTrainer().getId());

        if(trainer == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        TrainerCareer career = trainerCareerRepository.findById(careerId)
                .orElseThrow(() -> new IllegalStateException(ResponseMessage.NOT_EXISTS_CAREER));

        career.setCompanyName(dto.getCompanyName());
        career.setCompanyJoin(dto.getCompanyJoin());
        career.setCompanyQuit(dto.getCompanyQuit());

        TrainerCareer updatedCareer = trainerCareerRepository.save(career);

        data = TrainerCareerResponseDto.builder()
                .id(updatedCareer.getId())
                .trainerId(updatedCareer.getTrainer().getId())
                .companyName(updatedCareer.getCompanyName())
                .companyJoin(updatedCareer.getCompanyJoin())
                .companyQuit(updatedCareer.getCompanyQuit())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<TrainerCareerResponseDto> deleteTrainerCareer(Long id, Long careerId) {
        TrainerCareerResponseDto data = null;

        TrainerCareer career = trainerCareerRepository.findById(careerId)
                .orElseThrow(() -> new IllegalStateException(ResponseMessage.NOT_EXISTS_CAREER));

        trainerCareerRepository.delete(career);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<Void> deleteAllTrainerCareer(Long id) {
        User user = userService.getUserById(id);

        Trainer trainer = trainerService.getTrainerById(user.getTrainer().getId());

        List<TrainerCareer> careers = trainerCareerRepository.findByTrainerId(trainer.getId())
                .orElseThrow(() -> new IllegalStateException(ResponseMessage.NOT_EXISTS_CAREER));

        trainerCareerRepository.deleteAll(careers);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, null);
    }

    @Override
    public ResponseDto<List<TrainerCareerResponseDto>> getAllTrainerCareer(Long id) {
        User user = userService.getUserById(id);

        Trainer trainer = trainerService.getTrainerById(user.getTrainer().getId());

        List<TrainerCareer> careers = trainerCareerRepository.findByTrainerId(trainer.getId())
                .orElseThrow(() -> new IllegalStateException(ResponseMessage.NOT_EXISTS_CAREER));

        List<TrainerCareerResponseDto> data = careers.stream()
                .map(career -> TrainerCareerResponseDto.builder()
                        .id(career.getId())
                        .trainerId(career.getTrainer().getId())
                        .companyName(career.getCompanyName())
                        .companyJoin(career.getCompanyJoin())
                        .companyQuit(career.getCompanyQuit())
                        .build())
                .collect(Collectors.toList());
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<TrainerCareerResponseDto> getRecentTrainerCareer(Long id) {
        TrainerCareerResponseDto data = null;

        User user = userService.getUserById(id);

        Trainer trainer = trainerService.getTrainerById(user.getTrainer().getId());

        TrainerCareer career = trainerCareerRepository.findTopByTrainerIdOrderByCompanyQuitDesc(trainer.getId());

        data = TrainerCareerResponseDto.builder()
                .id(career.getId())
                .trainerId(career.getTrainer().getId())
                .companyName(career.getCompanyName())
                .companyJoin(career.getCompanyJoin())
                .companyQuit(career.getCompanyQuit())
                .build();
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }
}
