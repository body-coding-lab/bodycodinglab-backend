package com.bcl.fitmate.backend.service;

import com.bcl.fitmate.backend.dto.ResponseDto;

import java.util.Map;

public interface StatisticService {
    ResponseDto<Map<String, Long>> getStatistic();
}
