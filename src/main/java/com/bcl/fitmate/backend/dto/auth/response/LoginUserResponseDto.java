package com.bcl.fitmate.backend.dto.auth.response;

import com.bcl.fitmate.backend.common.enums.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginUserResponseDto {
    private String token;
    private long exprTime;
    private Long id;
    private UserRole role;
    private String username;
    private String name;
    private String profileImageUrl;
}
