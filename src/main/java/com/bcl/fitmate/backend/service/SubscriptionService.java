package com.bcl.fitmate.backend.service;


import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.payment.request.ConfirmPaymentRequestDto;
import com.bcl.fitmate.backend.dto.subscription.response.CreateSubscriptionResponseDto;
import com.bcl.fitmate.backend.dto.subscription.response.GetSubscriptionResponseDto;

public interface SubscriptionService {
    ResponseDto<CreateSubscriptionResponseDto> createSubscription(Long userId, ConfirmPaymentRequestDto dto);

    ResponseDto<GetSubscriptionResponseDto> getSubscription(Long userId);
}
