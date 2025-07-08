package com.bcl.fitmate.backend.dto.matchWatingList.response;

import com.bcl.fitmate.backend.common.enums.matchWaitingList.ApprovedStatus;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class GetMemberMatchWaitingListResponseDto {
    private Long matchWaitingListId;
    private Long trainerId;
    private String profileImageUrl;
    private String trainerName;
    private String trainerJobAddress;
    private String appliedAt;
    private ApprovedStatus approvedStatus;
    private String rejectResponse;
}
