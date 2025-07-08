package com.bcl.fitmate.backend.dto.auth.request;

import com.bcl.fitmate.backend.common.constants.Regex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetResetPasswordUserRequestDto {
    @NotBlank(message = "아이디는 필수 항목입니다.")
    @Pattern(regexp = Regex.USER_NAME, message = "아이디는 영문자로 시작하며, 총 5~12자 이내의 영문과 숫자 조합이어야 합니다.")
    private String username;

    @NotBlank(message = "이름은 필수 항목입니다.")
    @Pattern(regexp = Regex.NAME_KOREAN, message = "이름은 총 2~10자 이내의 한글이어야 합니다.")
    private String name;

    @NotNull(message = "생년월일은 필수 항목입니다.")
    @Past(message = "생년월일은 YYYY-MM-DD 형식이어야 합니다.")
    private LocalDate birthdate;

    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Pattern(regexp = Regex.EMAIL, message = "example@mail.com 형식이어야 합니다.")
    private String email;
}
