package com.bcl.fitmate.backend.controller;


import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.config.security.UserPrincipal;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.payment.request.CreatePaymentRequestDto;
import com.bcl.fitmate.backend.dto.payment.response.CreatePaymentResponseDto;
import com.bcl.fitmate.backend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.PAYMENT_API)
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    private static final String FAIL = "webHook/fail";

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping
    public ResponseEntity<ResponseDto<CreatePaymentResponseDto>> createPayment(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody CreatePaymentRequestDto dto
            ){
        Long userId = userPrincipal.getId();

        return ResponseDto.toResponseEntity(HttpStatus.CREATED, paymentService.createPayment(userId, dto));
    }

    @PostMapping(FAIL)
    public ResponseEntity<ResponseDto<Void>> paymentFailWebHook(@RequestParam String orderId){
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, paymentService.paymentFailWebHook(orderId));
    }
}
