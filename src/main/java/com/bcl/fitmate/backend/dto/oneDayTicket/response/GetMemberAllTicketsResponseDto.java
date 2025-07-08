package com.bcl.fitmate.backend.dto.oneDayTicket.response;

import com.bcl.fitmate.backend.common.enums.oneDayTicket.OneDayTicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Builder
public class GetMemberAllTicketsResponseDto {
    private Long id;
    private Long trainerId;
    private String trainerName;
    private String jobAddress;
    private LocalDate issuedAt;
    private LocalDate usedAt;
    private LocalDate canceledAt;
    private String cancelReason;
    private OneDayTicketStatus status;
    private String trainerProfileImageUrl;
}
