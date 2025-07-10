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
//    Optional<List<Coupon>> findByStatus(CouponStatus status);
//
//    List<Coupon> findByExpirationPeriodBeforeAndStatus(LocalDate date, CouponStatus status);
//
//    List<Coupon> findByStatusAndUsedDateBefore(CouponStatus status, LocalDateTime usedDate);
//
//    List<Coupon> findByStatusAndExpirationPeriodBefore(CouponStatus status, LocalDate date);
}
