package com.bcl.fitmate.backend.dto.auth.response;

import com.bcl.fitmate.backend.common.enums.trainer.TrainerStatus;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class LoginRejectedTrainerResponseDto extends LoginUserResponseDto {
    private final TrainerStatus trainerStatus;
}
