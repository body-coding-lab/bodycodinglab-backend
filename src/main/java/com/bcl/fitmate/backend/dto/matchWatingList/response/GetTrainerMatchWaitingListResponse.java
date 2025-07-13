package com.bcl.fitmate.backend.dto.matchWatingList.response;

import com.bcl.fitmate.backend.common.enums.matchWaitingList.ApprovedStatus;
import com.bcl.fitmate.backend.common.enums.user.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetTrainerMatchWaitingListResponse {
    private Long matchWaitingListId;
    private Long memberId;
    private String memberName;
    private int memberAge;
    private Gender memberGender;
    private String appliedAt;
    private ApprovedStatus approvedStatus;
}
