package com.bcl.fitmate.backend.dto.match.response;

import com.bcl.fitmate.backend.common.enums.user.Gender;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class GetTrainerMatchListResponseDto {
    private Long matchId;
    private Long memberId;
    private String memberName;
    private Gender memberGender;
}
