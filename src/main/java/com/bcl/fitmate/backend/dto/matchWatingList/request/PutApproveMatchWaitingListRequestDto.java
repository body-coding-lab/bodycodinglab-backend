package com.bcl.fitmate.backend.dto.matchWatingList.request;

import com.bcl.fitmate.backend.common.enums.matchWaitingList.ApprovedStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PutApproveMatchWaitingListRequestDto {
    private ApprovedStatus approvedStatus;
}
