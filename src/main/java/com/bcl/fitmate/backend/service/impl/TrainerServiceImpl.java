package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.common.enums.trainer.TrainerStatus;
import com.bcl.fitmate.backend.common.enums.uploadFile.TargetType;
import com.bcl.fitmate.backend.common.enums.user.UserRole;
import com.bcl.fitmate.backend.common.util.DateUtils;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.trainer.request.ReapplyTrainerRequestDto;
import com.bcl.fitmate.backend.dto.trainer.request.TrainerInfoRequestDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerInfoResponseDto;
import com.bcl.fitmate.backend.entity.Trainer;
import com.bcl.fitmate.backend.entity.User;
import com.bcl.fitmate.backend.repository.TrainerRepository;
import com.bcl.fitmate.backend.repository.UserRepository;
import com.bcl.fitmate.backend.service.TrainerService;
import com.bcl.fitmate.backend.service.UploadFileService;
import com.bcl.fitmate.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TrainerServiceImpl implements TrainerService {
    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UploadFileService uploadFileService;
    private final TrainerService trainerService;

    @Override
    public ResponseDto<TrainerInfoResponseDto> updateTrainerInfo(Long id, TrainerInfoRequestDto dto, List<MultipartFile> files) {
        TrainerInfoResponseDto data = null;

        User user = userService.getUserById(id);

        Trainer trainer = trainerService.getTrainerById(user.getTrainer().getId());

        Trainer trainerInfo = trainerRepository.findById(trainer.getId())
                .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.TRAINER_NOT_FOUND));

        String formattedEntrance = DateUtils.yearDateFormat(DateUtils.yearDateParse(dto.getEducationEntrance()));
        String formattedGraduate = DateUtils.yearDateFormat(DateUtils.yearDateParse(dto.getEducationGraduate()));

        trainerInfo.setJobAddress(dto.getJobAddress());
        trainerInfo.setShortIntroduce(dto.getShortIntroduce());
        trainerInfo.setLongIntroduce(dto.getLongIntroduce());
        trainerInfo.setEducationName(dto.getEducationName());
        trainerInfo.setEducationEntrance(formattedEntrance);
        trainerInfo.setEducationGraduate(formattedGraduate);

        Trainer updatedInfo = trainerRepository.save(trainerInfo);

        if(files != null && !files.isEmpty()) {
            List<MultipartFile> nonEmptyFiles = files.stream()
                    .filter(file -> !file.isEmpty())
                    .collect(Collectors.toList());

            if (!nonEmptyFiles.isEmpty()) {
                uploadFileService.uploadMultiFiles(nonEmptyFiles, user.getTrainer().getId(), TargetType.INFO);
            }
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
        User user = userService.getUserById(id);

        if(!user.getRole().getName().equals(UserRole.TRAINER)) {
            return ResponseDto.fail(ResponseCode.TRAINER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        user.getTrainer().setJobAddress(dto.getJobAddress());
        user.getTrainer().setTrainerStatus(TrainerStatus.PENDING);
//        user.getTrainer().setAttachmentFile(uploadFileService.updateSingleFile(fileId, user.getTrainer().getId()), TargetType.ATTACHMENT, attachmentFile);
        userRepository.save(user);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    @Transactional(readOnly = true)
    public Trainer getTrainerById(Long id) {
        return trainerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.TRAINER_NOT_FOUND));
    }
}
