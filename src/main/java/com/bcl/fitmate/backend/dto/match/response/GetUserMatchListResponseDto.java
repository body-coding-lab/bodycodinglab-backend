package com.bcl.fitmate.backend.dto.match.response;

import com.bcl.fitmate.backend.common.enums.user.Gender;
import com.bcl.fitmate.backend.common.enums.user.UserRole;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class GetUserMatchListResponseDto {
    private Long matchId;
    private UserRole role;
    private String name;
    private Gender gender;
    private int age;
}
