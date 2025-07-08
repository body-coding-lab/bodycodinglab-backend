package com.bcl.fitmate.backend.dto.match.response;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class GetMemberMatchResponseDto {
    private Long matchId;
    private Long trainerId;
    private String profileImageUrl;
    private String trainerName;
    private LocalDateTime matchedAt;
    private String trainerJobAddress;
}
