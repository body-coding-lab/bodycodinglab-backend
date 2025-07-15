package com.bcl.fitmate.backend.dto.payment.response;


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
