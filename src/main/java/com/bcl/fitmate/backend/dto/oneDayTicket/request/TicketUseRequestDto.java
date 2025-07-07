package com.bcl.fitmate.backend.dto.oneDayTicket.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class TicketUseRequestDto {
    private Long ticketId;
    private LocalDate usedAt;
}
