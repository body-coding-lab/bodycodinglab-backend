package com.bcl.fitmate.backend.dto.oneDayTicket.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetMemberAllTicketsResultDto {
    private int count;
    private List<GetMemberAllTicketsResponseDto> tickets;
}
