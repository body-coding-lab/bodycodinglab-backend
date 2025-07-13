package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.common.enums.matchWaitingList.ApprovedStatus;
import com.bcl.fitmate.backend.common.util.DateUtils;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.matchWatingList.request.PutApproveMatchWaitingListRequestDto;
import com.bcl.fitmate.backend.dto.matchWatingList.request.PutRejectMatchWaitingListRequestDto;
import com.bcl.fitmate.backend.dto.matchWatingList.response.CreateMatchWaitingListResponseDto;
import com.bcl.fitmate.backend.dto.matchWatingList.response.GetMemberMatchWaitingListResponseDto;
import com.bcl.fitmate.backend.dto.matchWatingList.response.GetTrainerMatchWaitingListResponse;
import com.bcl.fitmate.backend.entity.MatchWaitingList;
import com.bcl.fitmate.backend.entity.UploadFile;
import com.bcl.fitmate.backend.entity.User;
import com.bcl.fitmate.backend.repository.MatchWaitingListRepository;
import com.bcl.fitmate.backend.repository.UserRepository;
import com.bcl.fitmate.backend.service.MatchWaitingListService;
import com.bcl.fitmate.backend.service.UploadFileService;
import com.bcl.fitmate.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MatchWaitingListServiceImpl implements MatchWaitingListService {
    private final MatchWaitingListRepository matchWaitingListRepository;
    private final UserService userService;

    @Override
    public MatchWaitingList getMatchWaitingListById(Long matchWaitingListId) {
        return matchWaitingListRepository.findById(matchWaitingListId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));
    }

    @Override
    public MatchWaitingList getMatchWaitingListByMemberId(Long userId) {
        return matchWaitingListRepository.findByMember_Id(userId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.MEMBER_NOT_FOUND));
    }

    @Override
    @Transactional
    public ResponseDto<CreateMatchWaitingListResponseDto> createMatchWaitingList(Long trainerId, Long userId) {
        CreateMatchWaitingListResponseDto response = null;

        User trainer = userService.getUserById(trainerId);

        User member = userService.getUserById(userId);

        if(member.getMemberMatch() != null){
            throw new IllegalStateException(ResponseMessage.ALREADY_EXISTS_MATCH);
        }

        MatchWaitingList existing = getMatchWaitingListByMemberId(userId);
        if(existing != null && existing.getApprovedStatus() == ApprovedStatus.REJECT){
            member.setMatchWaitingListAsMember(null);
            trainer.removeMatchWaitingListAsTrainers(existing);

            matchWaitingListRepository.delete(existing);
            matchWaitingListRepository.flush();
        }

        MatchWaitingList matchWaitingList = MatchWaitingList.builder()
                .member(member)
                .trainer(trainer)
                .approvedStatus(ApprovedStatus.NOT_APPROVED)
                .build();

        member.setMatchWaitingListAsMember(matchWaitingList);
        trainer.addMatchWaitingListAsTrainers(matchWaitingList);

        matchWaitingListRepository.save(matchWaitingList);

        response = new CreateMatchWaitingListResponseDto(matchWaitingList.getId());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<GetMemberMatchWaitingListResponseDto> getMemberMatchWaitingList(Long userId) {
        GetMemberMatchWaitingListResponseDto response = null;

        MatchWaitingList matchWaitingList = getMatchWaitingListByMemberId(userId);

        User trainer = userService.getUserById(matchWaitingList.getTrainer().getId());

        String profileImageUrl = null;

        UploadFile trainerProfileImage = trainer.getProfileImage();
        if(trainerProfileImage != null){
            profileImageUrl = ApiMappingPattern.FILE_API + "/single/" + trainerProfileImage.getId();
        }


        response = new GetMemberMatchWaitingListResponseDto(
                matchWaitingList.getId(),
                matchWaitingList.getTrainer().getId(),
                profileImageUrl,
                matchWaitingList.getTrainer().getName(),
                matchWaitingList.getTrainer().getTrainer().getJobAddress(),
                DateUtils.format(matchWaitingList.getCreatedAt()),
                matchWaitingList.getApprovedStatus(),
                matchWaitingList.getRejectResponse()
        );

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }

    @Override
    @Transactional
    public ResponseDto<Void> matchCancel(Long userId, Long matchWaitingListId) {
        User member = userService.getUserById(userId);

        MatchWaitingList matchWaitingList = getMatchWaitingListById(matchWaitingListId);

        User trainer = userService.getUserById(matchWaitingList.getTrainer().getId());

        member.setMatchWaitingListAsMember(null);

        trainer.removeMatchWaitingListAsTrainers(matchWaitingList);

        matchWaitingListRepository.delete(matchWaitingList);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<List<GetTrainerMatchWaitingListResponse>> getTrainerMatchWaitingList(Long userId) {
        List<GetTrainerMatchWaitingListResponse> response = null;

        List<MatchWaitingList> lists = matchWaitingListRepository.findByTrainer_Id(userId);

        response = lists.stream()
                .map(list -> {
                    LocalDate birthdate = list.getMember().getBirthdate();
                    int age = Period.between(birthdate, LocalDate.now()).getYears();

                    return new GetTrainerMatchWaitingListResponse(
                            list.getId(),
                            list.getMember().getId(),
                            list.getMember().getName(),
                            age,
                            list.getMember().getGender(),
                            DateUtils.format(list.getCreatedAt()),
                            list.getApprovedStatus()
                    );
                }).toList();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }

    @Override
    @Transactional
    public ResponseDto<Void> matchApprove(Long userId, Long matchWaitingListId, PutApproveMatchWaitingListRequestDto dto) {
        MatchWaitingList matchWaitingList = getMatchWaitingListById(matchWaitingListId);

        if(!matchWaitingList.getTrainer().getId().equals(userId)){
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH_WAITING_LIST_PERMISSION, ResponseMessage.NOT_EXISTS_MATCH_WAITING_LIST_PERMISSION);
        }

        matchWaitingList.setApprovedStatus(dto.getApprovedStatus());

        matchWaitingListRepository.save(matchWaitingList);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    @Transactional
    public ResponseDto<Void> matchReject(Long userId, Long matchWaitingListId, PutRejectMatchWaitingListRequestDto dto) {
        MatchWaitingList matchWaitingList = getMatchWaitingListById(matchWaitingListId);

        if(!matchWaitingList.getTrainer().getId().equals(userId)){
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH_WAITING_LIST_PERMISSION, ResponseMessage.NOT_EXISTS_MATCH_WAITING_LIST_PERMISSION);
        }

        matchWaitingList.setApprovedStatus(dto.getApprovedStatus());
        matchWaitingList.setRejectResponse(dto.getRejectResponse());

        matchWaitingListRepository.save(matchWaitingList);
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }
}
