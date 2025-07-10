package com.bcl.fitmate.backend.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SignUpMemberResponseDto {
    private Long id;
    private String username;
}
