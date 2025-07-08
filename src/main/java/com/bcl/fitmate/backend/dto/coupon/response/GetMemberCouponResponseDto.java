package com.bcl.fitmate.backend.dto.coupon.response;

import com.bcl.fitmate.backend.common.enums.coupon.CouponStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GetMemberCouponResponseDto {
    private Long couponId;
    private String trainerNames;
    private LocalDate expirationPeriod;
    private CouponStatus status;
}
