package com.bcl.fitmate.backend.dto.payment.response;

import com.bcl.fitmate.backend.common.enums.payment.PaymentMethod;
import com.bcl.fitmate.backend.common.enums.payment.PaymentStatus;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentResponseDto {
  private Long paymentId;
  private String orderId;
  private int amount;
}
