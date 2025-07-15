package com.bcl.fitmate.backend.repository;

import com.bcl.fitmate.backend.common.enums.coupon.CouponStatus;
import com.bcl.fitmate.backend.entity.Board;
import com.bcl.fitmate.backend.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
     Optional<List<Coupon>> findByCouponStatus(CouponStatus status);

    List<Coupon> findByExpirationPeriodLessThanEqualAndCouponStatus(LocalDate date, CouponStatus status);

    List<Coupon> findByCouponStatusAndUsedDateBefore(CouponStatus status, LocalDateTime usedDate);

    List<Coupon> findByCouponStatusAndExpirationPeriodBefore(CouponStatus status, LocalDate date);
}
