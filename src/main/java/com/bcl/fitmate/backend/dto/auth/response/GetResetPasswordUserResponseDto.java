package com.bcl.fitmate.backend.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetResetPasswordUserResponseDto {
    private Long userId;
    private String email;
}
