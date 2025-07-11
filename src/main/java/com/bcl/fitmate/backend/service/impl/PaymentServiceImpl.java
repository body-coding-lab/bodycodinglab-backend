package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.common.enums.payment.PaymentMethod;
import com.bcl.fitmate.backend.common.enums.payment.PaymentStatus;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.payment.request.CreatePaymentRequestDto;
import com.bcl.fitmate.backend.dto.payment.response.CreatePaymentResponseDto;
import com.bcl.fitmate.backend.entity.Payment;
import com.bcl.fitmate.backend.entity.User;
import com.bcl.fitmate.backend.repository.PaymentRepository;
import com.bcl.fitmate.backend.repository.UserRepository;
import com.bcl.fitmate.backend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    public final UserRepository userRepository;
    public final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public ResponseDto<CreatePaymentResponseDto> createPayment(Long userId, CreatePaymentRequestDto dto) {
        User user = userRepository.findById(userId).orElse(null);

        if(user == null){
            return ResponseDto.fail(ResponseCode.MEMBER_NOT_FOUND, ResponseMessage.MEMBER_NOT_FOUND);
        }

        List<Payment> pendingPayments = paymentRepository.findByMemberAndStatus(user.getMember().getMemberId(), PaymentStatus.READY);
        List<Payment> failedPayments = paymentRepository.findByMemberAndStatus(user.getMember().getMemberId(), PaymentStatus.FAIL);
        pendingPayments.addAll(failedPayments);
        paymentRepository.deleteAll(pendingPayments);

        String orderId = "ORDER-" + UUID.randomUUID();

        Payment payment = Payment.builder()
                .orderId(orderId)
                .paymentStatus(PaymentStatus.READY)
                .amount(dto.getAmount())
                .member(user.getMember())
                .paymentMethod(PaymentMethod.KAKAO_PAY)
                .build();

        paymentRepository.save(payment);

        CreatePaymentResponseDto response = CreatePaymentResponseDto.builder()
                .paymentId(payment.getId())
                .orderId(orderId)
                .amount(dto.getAmount())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }

    @Override
    public ResponseDto<Void> paymentFailWebHook(String orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId).orElse(null);

        if(payment == null){
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_PAYMENT, ResponseMessage.NOT_EXISTS_PAYMENT);
        }

        if(payment.getPaymentStatus() == PaymentStatus.READY){
            payment.setPaymentStatus(PaymentStatus.FAIL);
        }

        paymentRepository.save(payment);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }
}
