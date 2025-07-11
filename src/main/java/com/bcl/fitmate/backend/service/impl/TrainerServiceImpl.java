package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.common.enums.uploadFile.TargetType;
import com.bcl.fitmate.backend.common.util.DateUtils;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.trainer.request.ReapplyTrainerRequestDto;
import com.bcl.fitmate.backend.dto.trainer.request.TrainerInfoRequestDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerCareerResponseDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerInfoResponseDto;
import com.bcl.fitmate.backend.entity.Trainer;
import com.bcl.fitmate.backend.entity.User;
import com.bcl.fitmate.backend.repository.TrainerRepository;
import com.bcl.fitmate.backend.repository.UserRepository;
import com.bcl.fitmate.backend.service.TrainerService;
import com.bcl.fitmate.backend.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TrainerServiceImpl implements TrainerService {
    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final UploadFileService uploadFileService;

    @Override
    public ResponseDto<TrainerInfoResponseDto> updateTrainerInfo(Long id, TrainerInfoRequestDto dto, List<MultipartFile> files) {
        TrainerInfoResponseDto data = null;

        User user = userRepository.findById(id)
                .orElse(null);

        if(user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        Trainer trainer = trainerRepository.findById(user.getTrainer().getId())
                .orElse(null);

        if(trainer == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        Trainer trainerInfo = trainerRepository.findById(trainer.getId())
                .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.TRAINER_NOT_FOUND));

        String formattedEntrance = DateUtils.yearDateFormat(DateUtils.yearDateParse(dto.getEducationEntrance()));
        String formattedGraduate = DateUtils.yearDateFormat(DateUtils.yearDateParse(dto.getEducationGraduate()));

        trainerInfo.setJobAddress(dto.getJobAddress());
        trainerInfo.setShortIntroduce(dto.getShortIntroduce());
        trainerInfo.setLongIntroduce(dto.getLongIntroduce());
        trainerInfo.setEducationName(dto.getEducationName());
        trainerInfo.setEducationEntrance(dto.getEducationEntrance());
        trainerInfo.setEducationGraduate(dto.getEducationGraduate());

        Trainer updatedInfo = trainerRepository.save(trainerInfo);

        if(files != null && !files.isEmpty()) {
            List<MultipartFile> nonEmptyFiles = files.stream()
                    .filter(file -> !file.isEmpty())
                    .collect(Collectors.toList());

            if(!nonEmptyFiles.isEmpty()) {
                uploadFileService.uploadMultiFiles(nonEmptyFiles, user.getTrainer().getId(), TargetType.INFO);
            }
        
        data = TrainerInfoResponseDto.builder()
                .id(updatedInfo.getId())
                .jobAddress(updatedInfo.getJobAddress())
                .shortIntroduce(updatedInfo.getShortIntroduce())
                .longIntroduce(updatedInfo.getLongIntroduce())
                .educationName(updatedInfo.getEducationName())
                .educationEntrance(updatedInfo.getEducationEntrance())
                .educationGraduate(updatedInfo.getEducationGraduate())
                .build();
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<Void> reapplyTrainer(Long id, ReapplyTrainerRequestDto dto, MultipartFile attachmentFile) {
        return null;
    }
}
