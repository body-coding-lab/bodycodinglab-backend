package com.bcl.fitmate.backend.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetMemberNameResponseDto {
    private Long memberId;
    private String username;
    private String name;
}
