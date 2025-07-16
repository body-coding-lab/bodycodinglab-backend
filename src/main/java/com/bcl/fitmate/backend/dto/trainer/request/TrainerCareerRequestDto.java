package com.bcl.fitmate.backend.dto.trainer.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class TrainerCareerRequestDto {
    @NotBlank(message = "회사명은 필수 항목입니다.")
    private String companyName;

    @NotNull(message = "입사일은 필수 항목입니다.")
    private LocalDate companyJoin;

    @NotNull(message = "퇴사일은 필수 항목입니다.")
    private LocalDate companyQuit;
}
