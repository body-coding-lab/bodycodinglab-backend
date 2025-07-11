package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.oneDayTicket.request.TicketCancelRequestDto;
import com.bcl.fitmate.backend.dto.oneDayTicket.request.TicketIssueRequestDto;
import com.bcl.fitmate.backend.dto.oneDayTicket.request.TicketUseRequestDto;
import com.bcl.fitmate.backend.dto.oneDayTicket.response.GetMemberAllTicketsResponseDto;
import com.bcl.fitmate.backend.dto.oneDayTicket.response.GetMemberAllTicketsResultDto;
import com.bcl.fitmate.backend.dto.oneDayTicket.response.GetTrainerAllTicketsResponseDto;
import com.bcl.fitmate.backend.entity.OneDayTicket;
import com.bcl.fitmate.backend.entity.UploadFile;
import com.bcl.fitmate.backend.entity.User;
import com.bcl.fitmate.backend.repository.MemberRepository;
import com.bcl.fitmate.backend.repository.OneDayTicketRepository;
import com.bcl.fitmate.backend.repository.TrainerRepository;
import com.bcl.fitmate.backend.service.CouponService;
import com.bcl.fitmate.backend.service.OneDayTicketService;
import com.bcl.fitmate.backend.service.UploadFileService;
import com.bcl.fitmate.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OneDayTicketServiceImpl implements OneDayTicketService {
    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;
    private final OneDayTicketRepository oneDayTicketRepository;
    private final UserService userService;
    private final UploadFileService uploadFileService;
    private final CouponService couponService;

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<GetMemberAllTicketsResultDto> getMemberAllTickets(Long id) {
        List<GetMemberAllTicketsResponseDto> ticketsResponseDtos = null;

        User user = userService.getUser(id);
        if (user == null) {
            return ResponseDto.fail(ResponseCode.USER_NOT_FOUND, ResponseCode.USER_NOT_FOUND);
        }

        List<OneDayTicket> tickets = oneDayTicketRepository.findByMemberId(id);

        ticketsResponseDtos = tickets.stream()
                .map(ticket -> {
                    String trainerProfileImageUrl = null;
                    UploadFile profileImage = user.getProfileImage();

                    if (profileImage != null) {
                        trainerProfileImageUrl = ApiMappingPattern.FILE_API + "/single/" + profileImage.getId();
                    }

                    return GetMemberAllTicketsResponseDto.builder()
                            .id(ticket.getId())
                            .trainerId(ticket.getTrainer().getId())
                            .trainerName(ticket.getTrainer().getName())
                            .jobAddress(ticket.getTrainer().getTrainer().getJobAddress())
                            .issuedAt(ticket.getIssuedAt())
                            .usedAt(ticket.getUsedAt())
                            .canceledAt(ticket.getCanceledAt())
                            .cancelReason(ticket.getCancelReason())
                            .status(ticket.getStatus())
                            .trainerProfileImageUrl(trainerProfileImageUrl)
                            .build();
                })
                .collect(Collectors.toList());

        GetMemberAllTicketsResultDto data = GetMemberAllTicketsResultDto.builder()
                .count(user.getMember().getOneDayTicketCount())
                .tickets(ticketsResponseDtos)
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<List<GetTrainerAllTicketsResponseDto>> getTrainerAllTickets(Long id) {
        return null;
    }

    @Override
    public ResponseDto<Void> issueOneDayTicket(Long id, TicketIssueRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<Void> useOneDayTicket(Long id, Long ticketId, TicketUseRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<Void> cancelOneDayTicket(Long id, Long ticketId, TicketCancelRequestDto dto) {
        return null;
    }
}
