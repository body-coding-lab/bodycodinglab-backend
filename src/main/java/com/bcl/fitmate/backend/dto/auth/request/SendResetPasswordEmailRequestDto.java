package com.bcl.fitmate.backend.dto.auth.request;

import com.bcl.fitmate.backend.common.constants.Regex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SendResetPasswordEmailRequestDto {
    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Pattern(regexp = Regex.EMAIL, message = "example@mail.com 형식이어야 합니다.")
    private String email;
}
