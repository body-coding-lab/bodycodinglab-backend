package com.bcl.fitmate.backend.controller.coupon;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.common.enums.coupon.CouponStatus;
import com.bcl.fitmate.backend.config.security.UserPrincipal;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.coupon.request.PutCouponRequestDto;
import com.bcl.fitmate.backend.dto.coupon.response.GetMemberCouponResponseDto;
import com.bcl.fitmate.backend.dto.coupon.response.GetTrainerCouponResponseDto;
import com.bcl.fitmate.backend.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.TRAINER_COUPON_API)
@RequiredArgsConstructor
public class TrainerCouponController {
    private final CouponService couponService;

    private static final String SELECT_COUPON = "/{couponId}";

    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping
    public ResponseEntity<ResponseDto<List<GetTrainerCouponResponseDto>>> getTrainerCoupons(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam CouponStatus status
    ){
       Long userId = userPrincipal.getId();
       return ResponseDto.toResponseEntity(HttpStatus.OK, couponService.getTrainerCoupons(userId, status));
    }


    @PreAuthorize("hasRole('TRAINER')")
    @PutMapping(SELECT_COUPON)
    public ResponseEntity<ResponseDto<Void>>  putTrainerCoupon(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long couponId,
            @Valid @RequestBody PutCouponRequestDto dto
    ){
      Long userId = userPrincipal.getId();
      return ResponseDto.toResponseEntity(HttpStatus.OK, couponService.putTrainerCoupon(userId, couponId, dto));
    }
}
