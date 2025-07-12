package com.bcl.fitmate.backend.controller.match;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.config.security.UserPrincipal;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.match.response.GetTrainerMatchListResponseDto;
import com.bcl.fitmate.backend.dto.match.response.GetTrainerMatchResponseDto;
import com.bcl.fitmate.backend.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.TRAINER_MATCH_API)
@RequiredArgsConstructor
public class TrainerMatchController {
    private final MatchService matchService;

    private static final String SELECT_MATCH = "/{matchId}";

    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping
    public ResponseEntity<ResponseDto<List<GetTrainerMatchListResponseDto>>> getTrainerMatchList(
            @AuthenticationPrincipal UserPrincipal userPrincipal
            ){
        Long userId = userPrincipal.getId();

        return ResponseDto.toResponseEntity(HttpStatus.OK, matchService.getTrainerMatchList(userId));
    }

    @PreAuthorize("hasRole('TRAINER')")
    @GetMapping(SELECT_MATCH)
    public ResponseEntity<ResponseDto<GetTrainerMatchResponseDto>> getTrainerMatch(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long matchId
    ){
        Long userId = userPrincipal.getId();

        return ResponseDto.toResponseEntity(HttpStatus.OK, matchService.getTrainerMatch(userId, matchId));
    }
}
