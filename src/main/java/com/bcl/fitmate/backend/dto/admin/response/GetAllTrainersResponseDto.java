package com.bcl.fitmate.backend.dto.admin.response;

import com.bcl.fitmate.backend.common.enums.trainer.TrainerStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class GetAllTrainersResponseDto {
    private Long trainerId;
    private String username;
    private String name;
    private LocalDate birthdate;
    private String jobAddress;
    private String createdAt;
    private TrainerStatus status;
}
