package com.bcl.fitmate.backend.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class GetMemberNameResponseDto {
    private Long memberId;
    private String username;
    private String name;
}
