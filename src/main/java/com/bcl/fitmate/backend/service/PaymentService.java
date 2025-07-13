package com.bcl.fitmate.backend.service;


import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.payment.request.CreatePaymentRequestDto;
import com.bcl.fitmate.backend.dto.payment.response.CreatePaymentResponseDto;
import com.bcl.fitmate.backend.entity.Payment;
import com.bcl.fitmate.backend.entity.User;

public interface PaymentService {

    Payment getPaymentByOrderId(String orderId);

    ResponseDto<CreatePaymentResponseDto> createPayment(Long userId, CreatePaymentRequestDto dto);

    ResponseDto<Void> paymentFailWebHook(String orderId);
}
