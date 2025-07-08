package com.bcl.fitmate.backend.dto.coupon.response;

import com.bcl.fitmate.backend.common.enums.coupon.CouponStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetTrainerCouponResponseDto {
    private Long couponId;
    private String memberName;
    private LocalDate expirationPeriod;
    private LocalDateTime usedDate;
    private CouponStatus status;

    public GetTrainerCouponResponseDto(Long couponId, String memberName, LocalDate expirationPeriod, CouponStatus status){
        this.couponId = couponId;
        this.memberName = memberName;
        this.expirationPeriod = expirationPeriod;
        this.status = status;
    }
}
