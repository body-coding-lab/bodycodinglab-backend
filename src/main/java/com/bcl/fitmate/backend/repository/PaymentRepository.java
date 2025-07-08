package com.bcl.fitmate.backend.repository;

import com.bcl.fitmate.backend.common.enums.payment.PaymentStatus;
import com.bcl.fitmate.backend.entity.Board;
import com.bcl.fitmate.backend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(String orderId);
    @Query("SELECT p FROM Payment p WHERE p.member.memberId = :memberId AND p.paymentStatus = :status")
    List<Payment> findByMemberAndStatus(@Param("memberId") Long memberId, @Param("status") PaymentStatus paymentStatus);



}
