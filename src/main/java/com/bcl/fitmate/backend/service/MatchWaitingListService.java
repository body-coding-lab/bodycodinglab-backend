package com.bcl.fitmate.backend.service;


import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.matchWatingList.request.PutApproveMatchWaitingListRequestDto;
import com.bcl.fitmate.backend.dto.matchWatingList.request.PutRejectMatchWaitingListRequestDto;
import com.bcl.fitmate.backend.dto.matchWatingList.response.CreateMatchWaitingListResponseDto;
import com.bcl.fitmate.backend.dto.matchWatingList.response.GetMemberMatchWaitingListResponseDto;
import com.bcl.fitmate.backend.dto.matchWatingList.response.GetTrainerMatchWaitingListResponse;

import java.util.List;

public interface MatchWaitingListService {

    ResponseDto<CreateMatchWaitingListResponseDto> createMatchWaitingList(Long trainerId, Long userId);

    ResponseDto<GetMemberMatchWaitingListResponseDto> getMemberMatchWaitingList(Long userId);

    ResponseDto<Void> matchCancel(Long userId, Long matchWaitingListId);

    ResponseDto<List<GetTrainerMatchWaitingListResponse>> getTrainerMatchWaitingList(Long userId);

    ResponseDto<Void> matchApprove(Long userId, Long matchWaitingListId, PutApproveMatchWaitingListRequestDto dto);

    ResponseDto<Void> matchReject(Long userId, Long matchWaitingListId, PutRejectMatchWaitingListRequestDto dto);
}
