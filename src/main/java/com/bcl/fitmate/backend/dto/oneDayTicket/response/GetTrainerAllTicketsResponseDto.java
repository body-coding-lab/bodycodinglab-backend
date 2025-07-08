package com.bcl.fitmate.backend.dto.oneDayTicket.response;

import com.bcl.fitmate.backend.common.enums.oneDayTicket.OneDayTicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class GetTrainerAllTicketsResponseDto {
    private Long id;
    private Long trainerId;
    private Long memberId;
    private String memberName;
    private String memberAddress;
    private LocalDate issuedAt;
    private LocalDate usedAt;
    private LocalDate canceledAt;
    private String cancelReason;
    private OneDayTicketStatus status;
    private int count;
}
