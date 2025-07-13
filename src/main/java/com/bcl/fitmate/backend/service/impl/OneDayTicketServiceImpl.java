package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.common.enums.oneDayTicket.OneDayTicketStatus;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.note.reqeust.NoteRequestDto;
import com.bcl.fitmate.backend.dto.oneDayTicket.request.TicketCancelRequestDto;
import com.bcl.fitmate.backend.dto.oneDayTicket.request.TicketIssueRequestDto;
import com.bcl.fitmate.backend.dto.oneDayTicket.request.TicketUseRequestDto;
import com.bcl.fitmate.backend.dto.oneDayTicket.response.GetMemberAllTicketsResponseDto;
import com.bcl.fitmate.backend.dto.oneDayTicket.response.GetMemberAllTicketsResultDto;
import com.bcl.fitmate.backend.dto.oneDayTicket.response.GetTrainerAllTicketsResponseDto;
import com.bcl.fitmate.backend.entity.*;
import com.bcl.fitmate.backend.repository.MemberRepository;
import com.bcl.fitmate.backend.repository.OneDayTicketRepository;
import com.bcl.fitmate.backend.repository.UserRepository;
import com.bcl.fitmate.backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OneDayTicketServiceImpl implements OneDayTicketService {
    private final MemberRepository memberRepository;
    private final TrainerService trainerService;
    private final OneDayTicketRepository oneDayTicketRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CouponService couponService;
    private final NoteService noteService;

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<GetMemberAllTicketsResultDto> getMemberAllTickets(Long id) {
        List<GetMemberAllTicketsResponseDto> ticketsResponseDtos = null;
        User user = userService.getUserById(id);
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
    @Transactional(readOnly = true)
    public ResponseDto<List<GetTrainerAllTicketsResponseDto>> getTrainerAllTickets(Long id) {
        User user = userService.getUserById(id);

        List<OneDayTicket> tickets = oneDayTicketRepository.findByTrainerId(id);

        List<GetTrainerAllTicketsResponseDto> data = tickets.stream()
                .map(ticket -> GetTrainerAllTicketsResponseDto.builder()
                        .id(ticket.getId())
                        .trainerId(ticket.getTrainer().getId())
                        .memberId(ticket.getMember().getId())
                        .memberName(ticket.getMember().getMember().getMemberAddress())
                        .issuedAt(ticket.getIssuedAt())
                        .usedAt(ticket.getUsedAt())
                        .canceledAt(ticket.getCanceledAt())
                        .status(ticket.getStatus())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    @Transactional
    public ResponseDto<Void> issueOneDayTicket(Long id, TicketIssueRequestDto dto) throws Exception {
        User user = userService.getUserById(id);

        Trainer trainer = trainerService.getTrainerById(user.getTrainer().getId());

        User memberUser = userRepository.findByUsernameAndName(dto.getUsername(), dto.getName())
                .orElse(null);

        if(memberUser == null) {
            return ResponseDto.fail(ResponseCode.MEMBER_NOT_FOUND, ResponseMessage.MEMBER_NOT_FOUND);
        }

        Member member = memberRepository.findByUser(memberUser)
                .orElse(null);

        if(member == null) {
            return ResponseDto.fail(ResponseCode.MEMBER_NOT_FOUND, ResponseMessage.MEMBER_NOT_FOUND);
        }

        validateOneDayTicketCount(member);

        OneDayTicket ticket = OneDayTicket.builder()
                .trainer(trainer.getUser())
                .member(member.getUser())
                .issuedAt(LocalDate.now())
                .status(OneDayTicketStatus.ISSUED)
                .build();

        oneDayTicketRepository.save(ticket);

        member.minusOneDayTicketCount();
        memberRepository.save(member);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    @Transactional
    public ResponseDto<Void> useOneDayTicket(Long id, Long ticketId, TicketUseRequestDto dto) throws Exception {
        OneDayTicket ticket = getTicket(ticketId);

        validateTrainer(ticket, id);
        validateTicketStatus(ticket, OneDayTicketStatus.ISSUED);

        ticket.setUsedAt(dto.getUsedAt());
        ticket.setStatus(OneDayTicketStatus.USED);

        oneDayTicketRepository.save(ticket);

        User memberUser = ticket.getMember();
        memberRepository.findByUserId(memberUser.getId())
                .orElseThrow(() -> new Exception(ResponseMessage.MEMBER_NOT_FOUND));

        couponService.createCoupon(ticket.getMember().getId());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    @Transactional
    public ResponseDto<Void> cancelOneDayTicket(Long id, Long ticketId, TicketCancelRequestDto dto) throws Exception {
        OneDayTicket ticket = getTicket(ticketId);

        validateTrainer(ticket, id);
        validateTicketStatus(ticket, OneDayTicketStatus.ISSUED);

        ticket.setCanceledAt(LocalDate.now());
        ticket.setCancelReason(dto.getCancelReason());
        ticket.setStatus(OneDayTicketStatus.CANCELED);

        oneDayTicketRepository.save(ticket);

        User memberUser = ticket.getMember();
        Member member = memberRepository.findByUserId(memberUser.getId())
                .orElseThrow(() -> new Exception(ResponseMessage.MEMBER_NOT_FOUND));

        member.plusOneDayTicketCount();
        memberRepository.save(member);

        User trainer = ticket.getTrainer();

        String title = "체험권이 취소되었습니다.";
        String content = "트레이너 [" + trainer.getName() +  "]님이 체험권을 취소했습니다. \n"
                + "사유: " + dto.getCancelReason();

        NoteRequestDto cancelReasonNote = NoteRequestDto.builder()
                .noteText("[" + title + "]\n" + content)
                .noteReceiver(memberUser.getId())
                .build();

        noteService.createNote(trainer.getId(), cancelReasonNote);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    private OneDayTicket getTicket(Long ticketId) throws Exception {
        return oneDayTicketRepository.findById(ticketId)
                .orElseThrow(() -> new Exception(ResponseMessage.NOT_EXISTS_ONE_DAY_TICKET));
    }

    private void validateOneDayTicketCount(Member member) throws Exception {
        if(member.getOneDayTicketCount() <= 0) {
            throw new Exception(ResponseMessage.NOT_TRIAL_CHANCE_LEFT);
        }
    }

    private void validateTrainer(OneDayTicket ticket, Long trainerId) throws Exception {
        if(!ticket.getTrainer().getId().equals(trainerId)) {
            throw new Exception(ResponseMessage.NOT_TRIAL_CHANCE_LEFT);
        }
    }

    private void validateTicketStatus(OneDayTicket ticket, OneDayTicketStatus ISSUED) throws Exception {
        if(ticket.getStatus() != ISSUED) {
            throw new Exception(ResponseMessage.NOT_TRIAL_CHANCE_LEFT);
        }
    }
}
