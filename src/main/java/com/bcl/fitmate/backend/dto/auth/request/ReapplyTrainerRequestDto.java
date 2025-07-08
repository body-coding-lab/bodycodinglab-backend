package com.bcl.fitmate.backend.dto.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReapplyTrainerRequestDto {
    @NotBlank(message = "근무지 주소는 필수 항목입니다.")
    private String jobAddress;
}
