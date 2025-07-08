package com.bcl.fitmate.backend.dto.auth.request;

import com.bcl.fitmate.backend.common.constants.Regex;
import com.bcl.fitmate.backend.common.enums.user.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SignUpMemberRequestDto {
    @NotBlank(message = "아이디는 필수 항목입니다.")
    @Pattern(regexp = Regex.USER_NAME, message = "아이디는 영문자로 시작하며, 총 5~12자 이내의 영문과 숫자 조합이어야 합니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Pattern(regexp = Regex.PASSWORD, message = "비밀번호는 영문/숫자/특수문자(~!@#$%^&*()-_=+)를 포함한 8~15자 이내이어야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 항목입니다.")
    private String confirmPassword;

    @NotBlank(message = "이름은 필수 항목입니다.")
    @Pattern(regexp = Regex.NAME_KOREAN, message = "이름은 총 2~10자 이내의 한글이어야 합니다.")
    private String name;

    @NotNull(message = "생년월일은 필수 항목입니다.")
    @Past(message = "생년월일은 YYYY-MM-DD 형식이어야 합니다.")
    private LocalDate birthdate;

    @NotNull(message = "성별은 필수 항목입니다.")
    private Gender gender;

    @NotBlank(message = "휴대폰 번호는 필수 항목입니다.")
    @Pattern(regexp = Regex.PHONE, message = "휴대폰 번호는 000-0000-0000 형식이어야 합니다.")
    private String phone;

    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Pattern(regexp = Regex.EMAIL, message = "example@mail.com 형식이어야 합니다.")
    private String email;

    @NotBlank(message = "주소는 필수 항목입니다.")
    private String memberAddress;
}
