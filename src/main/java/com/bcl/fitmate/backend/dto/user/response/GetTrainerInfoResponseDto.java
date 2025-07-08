package com.bcl.fitmate.backend.dto.user.response;

import com.bcl.fitmate.backend.common.enums.trainer.TrainerStatus;
import com.bcl.fitmate.backend.common.enums.user.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class GetTrainerInfoResponseDto {
    private Long trainerId;
    private String username;
    private String name;
    private LocalDate birthdate;
    private Gender gender;
    private String phone;
    private String email;
    private String jobAddress;
    private String attachmentFileUrl;
    private TrainerStatus trainerStatus;
}
