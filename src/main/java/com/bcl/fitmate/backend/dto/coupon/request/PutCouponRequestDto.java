package com.bcl.fitmate.backend.dto.coupon.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PutCouponRequestDto {
    @NotBlank
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String usedDate;
}
