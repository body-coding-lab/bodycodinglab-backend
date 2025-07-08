package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.common.enums.coupon.CouponStatus;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.coupon.response.CreateCouponResponseDto;
import com.bcl.fitmate.backend.entity.Coupon;
import com.bcl.fitmate.backend.entity.User;
import com.bcl.fitmate.backend.repository.CouponRepository;
import com.bcl.fitmate.backend.repository.UserRepository;
import com.bcl.fitmate.backend.service.CouponService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;

    public ResponseDto<CreateCouponResponseDto> creatCoupon(Long userId, Long trainerId){
        CreateCouponResponseDto response = null;

        User member = userRepository.findById(userId)
                .orElse(null);

        if(member == null){
            return ResponseDto.fail(ResponseCode.MEMBER_NOT_FOUND, ResponseMessage.MEMBER_NOT_FOUND);
        }

        User trainer = userRepository.findById(userId)
                .orElse(null);

        if(trainer == null){
            return ResponseDto.fail(ResponseCode.TRAINER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }


        Coupon coupon = new Coupon(
                null,
                member,
                trainer,
                LocalDate.now().plusMonths(3),
                null,
                CouponStatus.NOT_USED
        );

        //member.addMemberCoupons(coupon);
        couponRepository.save(coupon);

        response = new CreateCouponResponseDto(coupon.getId());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }
}
