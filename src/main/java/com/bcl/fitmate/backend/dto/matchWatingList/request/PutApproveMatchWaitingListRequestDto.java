package com.bcl.fitmate.backend.dto.matchWatingList.request;

import com.bcl.fitmate.backend.common.enums.matchWaitingList.ApprovedStatus;
import lombok.Getter;

@Getter
public class PutApproveMatchWaitingListRequestDto {
    private ApprovedStatus approvedStatus;
}
