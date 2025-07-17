package com.bcl.fitmate.backend.controller;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiMappingPattern.STATISTIC_API)
public class StatisticController {
    private final StatisticService statisticService;

    @GetMapping
    public ResponseEntity<ResponseDto<Map<String, Long>>> getStatistic() {
        return ResponseDto.toResponseEntity(HttpStatus.OK, statisticService.getStatistic());
    }
}
