package com.bcl.fitmate.backend.dto.auth.request;

import com.bcl.fitmate.backend.common.constants.Regex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ResetPasswordRequestDto {
    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Pattern(regexp = Regex.PASSWORD, message = "비밀번호는 영문/숫자/특수문자(~!@#$%^&*()-_=+)를 포함한 8~15자 이내이어야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 항목입니다.")
    private String confirmPassword;
}
