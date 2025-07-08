package com.bcl.fitmate.backend.dto.admin.request;

import com.bcl.fitmate.backend.common.enums.trainer.TrainerStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateTrainerStatusRequestDto {
    @NotNull(message = "상태를 선택해 주세요.")
    private TrainerStatus newStatus;

    @NotBlank(message = "거부 사유는 필수 항목입니다.")
    private String changeReason;
}
