package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.common.enums.user.UserRole;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.repository.MatchRepository;
import com.bcl.fitmate.backend.repository.UserRepository;
import com.bcl.fitmate.backend.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class StatisticServiceImpl implements StatisticService {
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;

    @Override
    public ResponseDto<Map<String, Long>> getStatistic() {
        long trainerCount = userRepository.countByRole_Name(UserRole.TRAINER);
        long memberCount = userRepository.countByRole_Name(UserRole.MEMBER);
        long matchCount = matchRepository.count();

        Map<String, Long> stats = new HashMap<>();
        stats.put("trainerCount", trainerCount);
        stats.put("memberCount", memberCount);
        stats.put("matchCount", matchCount);
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, stats);
    }
}
