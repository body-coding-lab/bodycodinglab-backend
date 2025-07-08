package com.bcl.fitmate.backend.dto.payment.response;

import com.bcl.fitmate.backend.common.enums.payment.PaymentMethod;
import com.bcl.fitmate.backend.common.enums.payment.PaymentStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmPaymentResponseDto {
    private Long paymentId;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
}
