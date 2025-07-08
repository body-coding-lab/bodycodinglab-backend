package com.bcl.fitmate.backend.service;

import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.oneDayTicket.request.TicketCancelRequestDto;
import com.bcl.fitmate.backend.dto.oneDayTicket.request.TicketIssueRequestDto;
import com.bcl.fitmate.backend.dto.oneDayTicket.request.TicketUseRequestDto;
import com.bcl.fitmate.backend.dto.oneDayTicket.response.GetMemberAllTicketsResultDto;
import com.bcl.fitmate.backend.dto.oneDayTicket.response.GetTrainerAllTicketsResponseDto;

import java.util.List;

public interface OneDayTicketService {
    ResponseDto<GetMemberAllTicketsResultDto> getMemberAllTickets(Long id);
    ResponseDto<List<GetTrainerAllTicketsResponseDto>> getTrainerAllTickets(Long id);
    ResponseDto<Void> issueOneDayTicket(Long id, TicketIssueRequestDto dto);
    ResponseDto<Void> useOneDayTicket(Long id, Long ticketId, TicketUseRequestDto dto);
    ResponseDto<Void> cancelOneDayTicket(Long id, Long ticketId, TicketCancelRequestDto dto);
}
