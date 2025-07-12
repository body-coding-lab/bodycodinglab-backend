package com.bcl.fitmate.backend.controller.match;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.config.security.UserPrincipal;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.match.response.GetMemberMatchResponseDto;
import com.bcl.fitmate.backend.dto.match.response.GetTrainerMatchResponseDto;
import com.bcl.fitmate.backend.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.MEMBER_MATCH_API)
@RequiredArgsConstructor
public class MemberMatchController {
    private final MatchService matchService;

    private static final String SELECT_MATCH = "/{matchId}";

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping
    public ResponseEntity<ResponseDto<GetMemberMatchResponseDto>> getMemberMatch(
            @AuthenticationPrincipal UserPrincipal userPrincipal
            ){
        Long userId = userPrincipal.getId();

        return ResponseDto.toResponseEntity(HttpStatus.OK, matchService.getMemberMatch(userId));
    }

    @PreAuthorize("hasRole('MEMBER')")
    @DeleteMapping(SELECT_MATCH)
    public ResponseEntity<ResponseDto<Void>> cancelMatch(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @PathVariable Long matchId
    ){
        Long userId = userPrincipal.getId();

        return ResponseDto.toResponseEntity(HttpStatus.OK, matchService.cancelMatch(userId, matchId));
    }
}
