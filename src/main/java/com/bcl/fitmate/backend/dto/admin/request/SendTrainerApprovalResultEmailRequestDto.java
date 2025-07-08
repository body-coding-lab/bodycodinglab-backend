package com.bcl.fitmate.backend.dto.admin.request;

import com.bcl.fitmate.backend.common.enums.trainer.TrainerStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SendTrainerApprovalResultEmailRequestDto {
    private String email;
    private TrainerStatus status;
    private String changeReason;
}
