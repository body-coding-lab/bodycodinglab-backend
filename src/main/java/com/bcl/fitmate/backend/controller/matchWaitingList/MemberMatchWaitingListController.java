package com.bcl.fitmate.backend.controller.matchWaitingList;


import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.config.security.UserPrincipal;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.matchWatingList.response.CreateMatchWaitingListResponseDto;
import com.bcl.fitmate.backend.dto.matchWatingList.response.GetMemberMatchWaitingListResponseDto;
import com.bcl.fitmate.backend.service.MatchWaitingListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.MEMBER_MATCH_WAITING_LIST_API)
@RequiredArgsConstructor
public class MemberMatchWaitingListController {
    private final MatchWaitingListService matchWaitingListService;

    private static final String CANCEL = "/{matchWaitingListId}";
    private static final String SELECT_TRAINER = "/{trainerId}";

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping(SELECT_TRAINER)
    public ResponseEntity<ResponseDto<CreateMatchWaitingListResponseDto>> createMatchWaitingList(
            @PathVariable Long trainerId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
            ){
        Long userId = userPrincipal.getId();

        return ResponseDto.toResponseEntity(HttpStatus.CREATED, matchWaitingListService.createMatchWaitingList(trainerId, userId));
    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping
    public ResponseEntity<ResponseDto<GetMemberMatchWaitingListResponseDto>> getMemberMatchWaitingList(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ){
        Long userId = userPrincipal.getId();

        return ResponseDto.toResponseEntity(HttpStatus.OK, matchWaitingListService.getMemberMatchWaitingList(userId));
    }


    @PreAuthorize("hasRole('MEMBER')")
    @DeleteMapping(CANCEL)
    public ResponseEntity<ResponseDto<Void>> matchCancel(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long matchWaitingListId
    ){
        Long userId = userPrincipal.getId();

        return ResponseDto.toResponseEntity(HttpStatus.OK, matchWaitingListService.matchCancel(userId, matchWaitingListId));
    }


}
