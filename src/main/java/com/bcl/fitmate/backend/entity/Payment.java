package com.bcl.fitmate.backend.entity;

import com.bcl.fitmate.backend.common.enums.payment.PaymentMethod;
import com.bcl.fitmate.backend.common.enums.payment.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "payment_key", nullable = false, unique = true)
    private String paymentKey;

    @Column(name = "order_id", nullable = false, unique = true)
    private String orderId;


    @Column(name = "payment_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    private int amount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;
}
