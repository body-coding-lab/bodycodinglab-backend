package com.bcl.fitmate.backend.controller.oneDayTicket;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.config.security.UserPrincipal;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.oneDayTicket.response.GetMemberAllTicketsResultDto;
import com.bcl.fitmate.backend.service.OneDayTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiMappingPattern.MEMBER_ONE_DAY_TICKET_API)
public class MemberOneDayTicketController {
    private final OneDayTicketService oneDayTicketService;

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping
    public ResponseEntity<ResponseDto<GetMemberAllTicketsResultDto>> getMemberAllTickets(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, oneDayTicketService.getMemberAllTickets(id));
    }
}
