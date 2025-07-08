package com.bcl.fitmate.backend.dto.auth.response;

import com.bcl.fitmate.backend.common.enums.trainer.TrainerStatus;
import com.bcl.fitmate.backend.common.enums.user.UserRole;
import lombok.Getter;

@Getter
public class LoginRejectedTrainerResponseDto extends LoginUserResponseDto {
    private TrainerStatus trainerStatus;

    public LoginRejectedTrainerResponseDto(
            String token,
            Long exprTime,
            Long id,
            UserRole role,
            String username,
            String name,
            String profileImageUrl,
            TrainerStatus trainerStatus
    ) {
        super(token, exprTime, id, role, username, name, profileImageUrl);
        this.trainerStatus = trainerStatus;
    }
}
