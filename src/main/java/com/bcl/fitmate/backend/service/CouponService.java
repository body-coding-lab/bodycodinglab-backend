package com.bcl.fitmate.backend.service;


import com.bcl.fitmate.backend.common.enums.coupon.CouponStatus;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.coupon.request.PutCouponRequestDto;
import com.bcl.fitmate.backend.dto.coupon.response.GetMemberCouponResponseDto;
import com.bcl.fitmate.backend.dto.coupon.response.GetTrainerCouponResponseDto;
import com.bcl.fitmate.backend.entity.Coupon;
import com.bcl.fitmate.backend.entity.User;
import jakarta.validation.Valid;

import java.util.List;

public interface CouponService {
    User getUserById(Long userId);

    Coupon getCouponById(Long couponId);

    public void createCoupon(Long memberId, Long trainerId);

    ResponseDto<List<GetMemberCouponResponseDto>> getMemberCoupons(Long userId, CouponStatus status);

    ResponseDto<Void> putMemberCoupon(Long userId, Long couponId);

    ResponseDto<List<GetTrainerCouponResponseDto>> getTrainerCoupons(Long userId, CouponStatus status);

    ResponseDto<Void> putTrainerCoupon(Long userId, Long couponId, @Valid PutCouponRequestDto dto);
}
