package com.bcl.fitmate.backend.dto.user.response;

import com.bcl.fitmate.backend.common.enums.trainer.TrainerStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetUserInfoResponseDto {
    private Long id;
    private String role;
    private String username;
    private String name;
    private String profileImageUrl;
    private TrainerStatus trainerStatus;
}
