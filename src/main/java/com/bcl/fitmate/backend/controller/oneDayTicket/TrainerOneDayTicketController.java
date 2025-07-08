package com.bcl.fitmate.backend.controller.oneDayTicket;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.config.security.UserPrincipal;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.oneDayTicket.request.TicketCancelRequestDto;
import com.bcl.fitmate.backend.dto.oneDayTicket.request.TicketIssueRequestDto;
import com.bcl.fitmate.backend.dto.oneDayTicket.request.TicketUseRequestDto;
import com.bcl.fitmate.backend.dto.oneDayTicket.response.GetTrainerAllTicketsResponseDto;
import com.bcl.fitmate.backend.service.OneDayTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiMappingPattern.TRAINER_ONE_DAY_TICKET_API)
public class TrainerOneDayTicketController {
    private final OneDayTicketService oneDayTicketService;

    private static final String ISSUE_TICKET = "/issued";
    private static final String USE_TICKET = "/{ticketId}/used";
    private static final String CANCEL_TICKET = "/{ticketId}/canceled";

    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping
    public ResponseEntity<ResponseDto<List<GetTrainerAllTicketsResponseDto>>> getTrainerAllTickets(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, oneDayTicketService.getTrainerAllTickets(id));
    }

    @PreAuthorize("hasRole('TRAINER')")
    @PostMapping(ISSUE_TICKET)
    public ResponseEntity<ResponseDto<Void>> issueOneDayTicket(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody TicketIssueRequestDto dto
    ) throws Exception {
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, oneDayTicketService.issueOneDayTicket(id, dto));
    }

    @PreAuthorize("hasRole('TRAINER')")
    @PostMapping(USE_TICKET)
    public ResponseEntity<ResponseDto<Void>> useOneDayTicket(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long ticketId,
            @RequestBody TicketUseRequestDto dto
    ) throws Exception {
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, oneDayTicketService.useOneDayTicket(id, ticketId, dto));
    }

    @PreAuthorize("hasRole('TRAINER')")
    @PostMapping(CANCEL_TICKET)
    public ResponseEntity<ResponseDto<Void>> cancelOneDayTicket(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long ticketId,
            @RequestBody TicketCancelRequestDto dto
    ) throws Exception {
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, oneDayTicketService.cancelOneDayTicket(id, ticketId, dto));
    }
}
