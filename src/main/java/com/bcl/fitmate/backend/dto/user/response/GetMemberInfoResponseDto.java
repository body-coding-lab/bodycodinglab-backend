package com.bcl.fitmate.backend.dto.user.response;

import com.bcl.fitmate.backend.common.enums.user.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class GetMemberInfoResponseDto {
    private String username;
    private String name;
    private LocalDate birthdate;
    private Gender gender;
    private String phone;
    private String email;
    private String memberAddress;
}
