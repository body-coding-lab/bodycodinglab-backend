package com.bcl.fitmate.backend.service;


import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.match.response.GetMemberMatchResponseDto;
import com.bcl.fitmate.backend.dto.match.response.GetTrainerMatchListResponseDto;
import com.bcl.fitmate.backend.dto.match.response.GetTrainerMatchResponseDto;
import com.bcl.fitmate.backend.dto.match.response.GetUserMatchResponseDto;
import com.bcl.fitmate.backend.entity.Match;
import com.bcl.fitmate.backend.entity.User;

import java.util.List;

public interface MatchService {

    Match getMatchById(Long matchId);

    ResponseDto<GetMemberMatchResponseDto> getMemberMatch(Long userId);

    ResponseDto<Void> cancelMatch(Long userId, Long matchId);

    ResponseDto<List<GetTrainerMatchListResponseDto>> getTrainerMatchList(Long userId);

    ResponseDto<GetTrainerMatchResponseDto> getTrainerMatch(Long userId, Long matchId);

    ResponseDto<List<GetUserMatchResponseDto>> getUserMatchList(Long userId);
}
