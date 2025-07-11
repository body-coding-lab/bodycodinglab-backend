package com.bcl.fitmate.backend.controller;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.config.security.UserPrincipal;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.payment.request.ConfirmPaymentRequestDto;
import com.bcl.fitmate.backend.dto.subscription.response.CreateSubscriptionResponseDto;
import com.bcl.fitmate.backend.dto.subscription.response.GetSubscriptionResponseDto;
import com.bcl.fitmate.backend.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.MEMBER_SUBSCRIPTION_API)
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping
    public ResponseEntity<ResponseDto<CreateSubscriptionResponseDto>> createSubscription(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody ConfirmPaymentRequestDto dto
            ){
        Long userId = userPrincipal.getId();

        return ResponseDto.toResponseEntity(HttpStatus.CREATED, subscriptionService.createSubscription(userId, dto));
    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping
    public ResponseEntity<ResponseDto<GetSubscriptionResponseDto>> getSubscription(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ){
      Long userId = userPrincipal.getId();

      return ResponseDto.toResponseEntity(HttpStatus.OK, subscriptionService.getSubscription(userId));
    }

}
