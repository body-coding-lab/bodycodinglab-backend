package com.bcl.fitmate.backend.dto.subscription.response;

import com.bcl.fitmate.backend.common.enums.member.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetSubscriptionResponseDto {
    private String memberName;
    private int price;
    private String paymentDate;
    private MemberStatus status;
}
