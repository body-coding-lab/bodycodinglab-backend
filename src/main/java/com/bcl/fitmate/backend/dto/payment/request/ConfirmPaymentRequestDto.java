package com.bcl.fitmate.backend.dto.payment.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmPaymentRequestDto {
    private String paymentKey;
    private String orderId;
    private String provider;
    private Long matchWaitingListId;
}
