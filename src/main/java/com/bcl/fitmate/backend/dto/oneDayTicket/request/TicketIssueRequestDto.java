package com.bcl.fitmate.backend.dto.oneDayTicket.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TicketIssueRequestDto {
    private String username;
    private String name;
}
