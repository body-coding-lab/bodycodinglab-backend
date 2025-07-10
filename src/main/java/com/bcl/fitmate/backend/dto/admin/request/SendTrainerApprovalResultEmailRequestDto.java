package com.bcl.fitmate.backend.dto.admin.request;

import com.bcl.fitmate.backend.common.enums.trainer.TrainerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendTrainerApprovalResultEmailRequestDto {
    private String email;
    private TrainerStatus status;
    private String changeReason;
}
