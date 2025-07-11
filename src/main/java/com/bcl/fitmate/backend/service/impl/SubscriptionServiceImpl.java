package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.common.enums.member.MemberStatus;
import com.bcl.fitmate.backend.common.enums.payment.PaymentMethod;
import com.bcl.fitmate.backend.common.enums.payment.PaymentStatus;
import com.bcl.fitmate.backend.common.util.DateUtils;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.payment.request.ConfirmPaymentRequestDto;
import com.bcl.fitmate.backend.dto.subscription.response.CreateSubscriptionResponseDto;
import com.bcl.fitmate.backend.dto.subscription.response.GetSubscriptionResponseDto;
import com.bcl.fitmate.backend.entity.*;
import com.bcl.fitmate.backend.repository.*;
import com.bcl.fitmate.backend.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final MatchWaitingListRepository matchWaitingListRepository;
    private final MatchRepository matchRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public ResponseDto<CreateSubscriptionResponseDto> createSubscription(Long userId, ConfirmPaymentRequestDto dto) {
        CreateSubscriptionResponseDto response = null;

        Payment payment = paymentRepository.findByOrderId(dto.getOrderId()).orElse(null);

        if(payment == null){
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_PAYMENT, ResponseMessage.NOT_EXISTS_PAYMENT);
        }

        if(payment.getPaymentStatus() != PaymentStatus.READY){
            return ResponseDto.fail(ResponseCode.NOT_PROCESS_STATUS_PAYMENT, ResponseMessage.NOT_PROCESS_STATUS_PAYMENT);
        }

        PaymentMethod method = PaymentMethod.valueOf(dto.getProvider().toUpperCase());

        payment.setPaymentKey(dto.getPaymentKey());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setPaymentMethod(method);

        Member member = payment.getMember();

        Subscription subscription = Subscription.builder()
                .member(member)
                .price(payment.getAmount())
                .paymentDate(DateUtils.parse(DateUtils.format(LocalDateTime.now())))
                .build();

        subscriptionRepository.save(subscription);

        payment.setSubscription(subscription);

        member.setSubscription(subscription);
        member.setStatus(MemberStatus.SUBSCRIPTION);

        MatchWaitingList matchWaitingList = matchWaitingListRepository.findByMember_Id(userId).orElse(null);

        if(matchWaitingList == null){
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH_WAITING_LIST, ResponseMessage.NOT_EXISTS_MATCH_WAITING_LIST);
        }

        User trainer = userRepository.findById(matchWaitingList.getTrainer().getId()).orElse(null);

        if(trainer == null){
            return ResponseDto.fail(ResponseCode.TRAINER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        Match match = Match.builder()
                .member(matchWaitingList.getMember())
                .trainer(trainer)
                .matchedAt(DateUtils.parse(DateUtils.format(LocalDateTime.now())))
                .isMaintained(true)
                .build();

        match.getMember().setMemberMatch(match);
        trainer.addTrainerMatches(match);
        matchRepository.save(match);

        match.getMember().setMatchWaitingListAsMember(null);
        trainer.removeMatchWaitingListAsTrainers(matchWaitingList);
        matchWaitingListRepository.delete(matchWaitingList);

        response = new CreateSubscriptionResponseDto(subscription.getId());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }

    @Override
    public ResponseDto<GetSubscriptionResponseDto> getSubscription(Long userId) {
        return null;
    }
}
