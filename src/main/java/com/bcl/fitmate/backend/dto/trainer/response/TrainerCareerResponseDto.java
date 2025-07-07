package com.bcl.fitmate.backend.dto.trainer.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class TrainerCareerResponseDto {
    private Long id;
    private Long trainerId;
    private String companyName;
    private LocalDate companyJoin;
    private LocalDate companyQuit;
}
