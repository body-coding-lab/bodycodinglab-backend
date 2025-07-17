package com.bcl.fitmate.backend.controller.match;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.config.security.UserPrincipal;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.match.response.GetUserMatchListResponseDto;
import com.bcl.fitmate.backend.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.USER_MATCH_API)
@RequiredArgsConstructor
public class UserMatchController {
    private final MatchService matchService;


    @PreAuthorize("hasAnyRole('MEMBER', 'TRAINER')")
    @GetMapping
    public ResponseEntity<ResponseDto<List<GetUserMatchListResponseDto>>> getUserMatchList(
            @AuthenticationPrincipal UserPrincipal userPrincipal
            ){
        Long userId = userPrincipal.getId();

        return ResponseDto.toResponseEntity(HttpStatus.OK, matchService.getUserMatchList(userId));
    }
}
