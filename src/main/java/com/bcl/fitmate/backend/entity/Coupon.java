package com.bcl.fitmate.backend.entity;

import com.bcl.fitmate.backend.common.enums.coupon.CouponStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Coupon extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id" , nullable = false)
    private User member;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private User trainer;

    @Column(name = "expiration_period", nullable = false)
    private LocalDate expirationPeriod;


    @Column(name = "used_date", insertable = false, updatable = false)
    private LocalDateTime usedDate;

    @Column(name = "coupon_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponStatus couponStatus;
}
