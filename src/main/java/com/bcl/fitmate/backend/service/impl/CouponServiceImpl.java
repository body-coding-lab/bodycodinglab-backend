package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.common.enums.coupon.CouponStatus;
import com.bcl.fitmate.backend.common.util.DateUtils;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.coupon.request.PutCouponRequestDto;
import com.bcl.fitmate.backend.dto.coupon.response.GetMemberCouponResponseDto;
import com.bcl.fitmate.backend.dto.coupon.response.GetTrainerCouponResponseDto;
import com.bcl.fitmate.backend.entity.Coupon;
import com.bcl.fitmate.backend.entity.User;
import com.bcl.fitmate.backend.repository.CouponRepository;
import com.bcl.fitmate.backend.repository.UserRepository;
import com.bcl.fitmate.backend.service.CouponService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void changeExpireCoupons(){
        LocalDate today = LocalDate.now();
        List<Coupon> couponsToExpire = couponRepository.findByExpirationPeriodBeforeAndCouponStatus(today, CouponStatus.NOT_USED);

       for (Coupon coupon : couponsToExpire){
           coupon.setCouponStatus(CouponStatus.EXPIRED);

           User member = coupon.getMember();
           if(member != null){
               member.removeMemberCoupons(coupon);
               member.addMemberCoupons(coupon);
           }
       }
        couponRepository.flush();
    }

    @Transactional
    @Scheduled(cron = "0 0 1 * * *")
    public void deleteExpireCoupon() {
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);

        List<Coupon> expiredCouponsToDelete = couponRepository.findByCouponStatusAndExpirationPeriodBefore(CouponStatus.EXPIRED, sixMonthsAgo);

        for (Coupon coupon : expiredCouponsToDelete) {
            User member = coupon.getMember();
            if (member != null) {
                member.removeMemberCoupons(coupon);
            }
        }
        couponRepository.flush();
    }

    @Transactional
    @Scheduled(cron = "0 0 2 * * *")
    public void deleteCompleteCoupon(){
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
        LocalDateTime cutoffDateTime = sixMonthsAgo.atStartOfDay();

        List<Coupon> oldCompleteCoupons = couponRepository.findByCouponStatusAndUsedDateBefore(CouponStatus.COMPLETE, cutoffDateTime);

        for(Coupon coupon : oldCompleteCoupons){
            User trainer = coupon.getTrainer();
            if(trainer != null){
                trainer.removeTrainerCoupons(coupon);
            }
        }

        couponRepository.deleteAll(oldCompleteCoupons);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));
    }

    @Override
    public Coupon getCouponById(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_COUPON));
    }

    @Override
    @Transactional
    public void createCoupon(Long userId, Long trainerId){
        User member = getUserById(userId);

        User trainer = getUserById(trainerId);

        Coupon coupon = Coupon.builder()
                .member(member)
                .trainer(trainer)
                .expirationPeriod(LocalDate.now().plusMonths(3))
                .usedDate(null)
                .couponStatus(CouponStatus.NOT_USED)
                .build();

        member.addMemberCoupons(coupon);
        couponRepository.save(coupon);
    }



    @Override
    public ResponseDto<List<GetMemberCouponResponseDto>> getMemberCoupons(Long userId, CouponStatus status) {
        List<GetMemberCouponResponseDto> responseCoupons = null;

        User member = getUserById(userId);


        List<Coupon> coupons = member.getMemberCoupons();

        List<Coupon> memberCoupons = coupons.stream()
                .filter(coupon -> coupon.getCouponStatus().equals(status))
                .toList();

        responseCoupons = memberCoupons.stream()
                .map(coupon -> new GetMemberCouponResponseDto(
                        coupon.getId(),
                        coupon.getTrainer().getName(),
                        coupon.getExpirationPeriod(),
                        coupon.getCouponStatus()
                )).toList();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseCoupons);
    }

    @Override
    @Transactional
    public ResponseDto<Void> putMemberCoupon(Long userId, Long couponId) {
        Coupon coupon = getCouponById(couponId);


        if(!coupon.getMember().getId().equals(userId)){
            throw new EntityNotFoundException(ResponseMessage.NOT_EXISTS_COUPON_PERMISSION);
        }

        coupon.setCouponStatus(CouponStatus.APPLICATION);
        couponRepository.save(coupon);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    public ResponseDto<List<GetTrainerCouponResponseDto>> getTrainerCoupons(Long userId, CouponStatus status) {
        List<GetTrainerCouponResponseDto> responseCoupons = null;

        User trainer = getUserById(userId);

        List<Coupon> coupons = trainer.getTrainerCoupons();

        List<Coupon> trainerCoupons = coupons.stream()
                .filter(coupon -> coupon.getCouponStatus().equals(status))
                .toList();

        if(status == CouponStatus.APPLICATION){
            responseCoupons = trainerCoupons.stream()
                    .map(coupon -> new GetTrainerCouponResponseDto(
                            coupon.getId(),
                            coupon.getMember().getName(),
                            coupon.getExpirationPeriod(),
                            coupon.getCouponStatus()
                    )).collect(Collectors.toList());
        }else if(status == CouponStatus.COMPLETE){
            responseCoupons = trainerCoupons.stream()
                    .map(coupon -> new GetTrainerCouponResponseDto(
                            coupon.getId(),
                            coupon.getMember().getName(),
                            coupon.getExpirationPeriod(),
                            coupon.getUsedDate(),
                            coupon.getCouponStatus()
                    )).collect(Collectors.toList());
        }

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseCoupons);
    }

    @Override
    @Transactional
    public ResponseDto<Void> putTrainerCoupon(Long userId, Long couponId, PutCouponRequestDto dto) {
        Coupon coupon = getCouponById(couponId);


        if(!coupon.getTrainer().getId().equals(userId)){
            throw new AccessDeniedException(ResponseMessage.NOT_EXISTS_COUPON_PERMISSION);
        }

        LocalDateTime usedDate = LocalDate.parse(dto.getUsedDate(), DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();

        coupon.setUsedDate(DateUtils.parse(DateUtils.format(usedDate)));
        coupon.setCouponStatus(CouponStatus.COMPLETE);

        couponRepository.save(coupon);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }
}
