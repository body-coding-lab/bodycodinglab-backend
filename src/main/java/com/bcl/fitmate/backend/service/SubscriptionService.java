package com.bcl.fitmate.backend.service;


import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.payment.request.ConfirmPaymentRequestDto;
import com.bcl.fitmate.backend.dto.subscription.response.CreateSubscriptionResponseDto;
import com.bcl.fitmate.backend.dto.subscription.response.GetSubscriptionResponseDto;
import com.bcl.fitmate.backend.entity.MatchWaitingList;
import com.bcl.fitmate.backend.entity.Payment;
import com.bcl.fitmate.backend.entity.Subscription;
import com.bcl.fitmate.backend.entity.User;

public interface SubscriptionService {
    User getUserById(Long userId);

    Payment getPaymentByOrderId(String orderId);

    Subscription getSubscriptionByMember_MemberId(Long memberId);

    MatchWaitingList getMatchWaitingListByMemberId(Long userId);

    ResponseDto<CreateSubscriptionResponseDto> createSubscription(Long userId, ConfirmPaymentRequestDto dto);

    ResponseDto<GetSubscriptionResponseDto> getSubscription(Long userId);
}
