package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.common.enums.trainer.TrainerStatus;
import com.bcl.fitmate.backend.common.util.DateUtils;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.admin.request.SendTrainerApprovalResultEmailRequestDto;
import com.bcl.fitmate.backend.dto.admin.request.UpdateTrainerStatusRequestDto;
import com.bcl.fitmate.backend.dto.admin.response.GetAllTrainersResponseDto;
import com.bcl.fitmate.backend.dto.admin.response.GetTrainerDetailResponseDto;
import com.bcl.fitmate.backend.entity.*;
import com.bcl.fitmate.backend.repository.TrainerListViewRepository;
import com.bcl.fitmate.backend.repository.TrainerRepository;
import com.bcl.fitmate.backend.repository.TrainerStatusLogRepository;
import com.bcl.fitmate.backend.service.AdminService;
import com.bcl.fitmate.backend.service.MailService;
import com.bcl.fitmate.backend.service.UploadFileService;
import com.bcl.fitmate.backend.service.UserService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {
    private final TrainerRepository trainerRepository;
    private final TrainerStatusLogRepository trainerStatusLogRepository;
    private final TrainerListViewRepository trainerListViewRepository;
    private final UserService userService;
    private final MailService mailService;
    private final UploadFileService uploadFileService;

    @Override
    public ResponseDto<Page<GetAllTrainersResponseDto>> getAllTrainers(int page, int size, TrainerStatus trainerStatus) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("trainerId").descending());
        Page<TrainerListView> trainerPage = null;

        if (trainerStatus == null) {
            trainerPage = trainerListViewRepository.findAll(pageable);
        } else {
            trainerPage = trainerListViewRepository.findByStatus(trainerStatus, pageable);
        }

        Page<GetAllTrainersResponseDto> data = trainerPage.map(this::toGetAllTrainersResponseDto);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<GetTrainerDetailResponseDto> getTrainerDetail(Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElse(null);

        if (trainer == null) {
            return ResponseDto.fail(ResponseCode.TRAINER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        String attachmentFileUrl = null;
        UploadFile attachmentFile = trainer.getAttachmentFile();
        if (attachmentFile != null) {
            attachmentFileUrl = ApiMappingPattern.FILE_API + "/trainer-attachment/" + attachmentFile.getId() + "/" + attachmentFile.getFileType();
        }

        String profileImageUrl = null;
        UploadFile profileImage = trainer.getUser().getProfileImage();
        if (attachmentFile != null) {
            profileImageUrl = ApiMappingPattern.FILE_API + "/profile/" + profileImage.getId() + "/" + profileImage.getFileType();
        }

        GetTrainerDetailResponseDto data = GetTrainerDetailResponseDto.builder()
                .trainerId(trainer.getId())
                .username(trainer.getUser().getUsername())
                .name(trainer.getUser().getName())
                .birthdate(trainer.getUser().getBirthdate())
                .gender(trainer.getUser().getGender())
                .phone(trainer.getUser().getPhone())
                .email(trainer.getUser().getEmail())
                .jobAddress(trainer.getJobAddress())
                .attachmentFileUrl(attachmentFileUrl)
                .createdAt(DateUtils.format(trainer.getCreatedAt()))
                .status(trainer.getTrainerStatus())
                .profileImageUrl(profileImageUrl)
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    @Transactional
    public ResponseDto<GetTrainerDetailResponseDto> updateTrainerStatus(Long id, Long trainerId, UpdateTrainerStatusRequestDto dto) throws MessagingException {
        User user = userService.getUser(id);

        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseMessage.USER_NOT_FOUND);
        }

        Trainer trainer = trainerRepository.findById(trainerId)
                .orElse(null);

        if (trainer == null) {
            return ResponseDto.fail(ResponseCode.TRAINER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        if (trainer.getTrainerStatus().equals(dto.getNewStatus())) {
            return ResponseDto.fail(ResponseCode.ALREADY_EQUAL_STATUS, ResponseMessage.ALREADY_EQUAL_STATUS);
        }

        String attachmentFileUrl = null;
        UploadFile attachmentFile = trainer.getAttachmentFile();
        if (attachmentFile != null) {
            attachmentFileUrl = ApiMappingPattern.FILE_API + "/trainer-attachment/" + attachmentFile.getId() + "/" + attachmentFile.getFileType();
        }

        String profileImageUrl = null;
        UploadFile profileImage = trainer.getUser().getProfileImage();
        if (attachmentFile != null) {
            profileImageUrl = ApiMappingPattern.FILE_API + "/profile/" + profileImage.getId() + "/" + profileImage.getFileType();
        }

        TrainerStatus prevStatus = trainer.getTrainerStatus();

        trainer.setTrainerStatus(dto.getNewStatus());
        Trainer savedTrainer = trainerRepository.save(trainer);

        createLog(user, savedTrainer, prevStatus, dto.getChangeReason());

        SendTrainerApprovalResultEmailRequestDto sendEmailDto = SendTrainerApprovalResultEmailRequestDto.builder()
                .email(savedTrainer.getUser().getEmail())
                .status(dto.getNewStatus())
                .changeReason(dto.getChangeReason())
                .build();

        mailService.sendTrainerApprovalResultEmail(sendEmailDto);

        GetTrainerDetailResponseDto data = GetTrainerDetailResponseDto.builder()
                .trainerId(savedTrainer.getId())
                .username(savedTrainer.getUser().getUsername())
                .name(savedTrainer.getUser().getName())
                .birthdate(savedTrainer.getUser().getBirthdate())
                .gender(savedTrainer.getUser().getGender())
                .phone(savedTrainer.getUser().getPhone())
                .email(savedTrainer.getUser().getEmail())
                .jobAddress(savedTrainer.getJobAddress())
                .attachmentFileUrl(attachmentFileUrl)
                .createdAt(DateUtils.format(savedTrainer.getCreatedAt()))
                .status(savedTrainer.getTrainerStatus())
                .profileImageUrl(profileImageUrl)
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    private GetAllTrainersResponseDto toGetAllTrainersResponseDto(TrainerListView view) {
        return GetAllTrainersResponseDto.builder()
                .trainerId(view.getTrainerId())
                .username(view.getUsername())
                .name(view.getName())
                .birthdate(view.getBirthdate())
                .jobAddress(view.getJobAddress())
                .createdAt(DateUtils.format(view.getCreatedAt()))
                .status(view.getStatus())
                .build();
    }

    private void createLog(User user, Trainer savedTrainer, TrainerStatus prevStatus, String changeReason) {
        TrainerStatusLog trainerStatusLog = new TrainerStatusLog(user, savedTrainer, prevStatus, changeReason);
    }
}
