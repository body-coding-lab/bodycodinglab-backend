package com.bcl.fitmate.backend.dto.admin.response;

import com.bcl.fitmate.backend.common.enums.trainer.TrainerStatus;
import com.bcl.fitmate.backend.common.enums.user.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class GetTrainerDetailResponseDto {
    private Long trainerId;
    private String username;
    private String name;
    private LocalDate birthdate;
    private Gender gender;
    private String phone;
    private String email;
    private String jobAddress;
    private String attachmentFileUrl;
    private String createdAt;
    private TrainerStatus status;
    private String profileImageUrl;
}
