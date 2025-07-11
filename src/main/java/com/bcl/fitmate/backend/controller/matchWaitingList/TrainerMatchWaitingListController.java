package com.bcl.fitmate.backend.controller.matchWaitingList;


import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.config.security.UserPrincipal;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.matchWatingList.request.PutApproveMatchWaitingListRequestDto;
import com.bcl.fitmate.backend.dto.matchWatingList.request.PutRejectMatchWaitingListRequestDto;
import com.bcl.fitmate.backend.dto.matchWatingList.response.GetTrainerMatchWaitingListResponse;
import com.bcl.fitmate.backend.service.MatchWaitingListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.TRAINER_MATCH_WAITING_LIST_API)
@RequiredArgsConstructor
public class TrainerMatchWaitingListController {
   private final MatchWaitingListService matchWaitingListService;

   private static final String APPROVE = "/{matchWaitingListId}/approves";
   private static final String REJECT = "/{matchWaitingListId}/rejects";

   @PreAuthorize("hasRole('TRAINER')")
   @GetMapping
   public ResponseEntity<ResponseDto<List<GetTrainerMatchWaitingListResponse>>> getTrainerMatchWaitingList(
           @AuthenticationPrincipal UserPrincipal userPrincipal
           ){
       Long userId = userPrincipal.getId();

       return ResponseDto.toResponseEntity(HttpStatus.OK, matchWaitingListService.getTrainerMatchWaitingList(userId));
   }

   @PreAuthorize("hasRole('TRAINER')")
   @PutMapping(APPROVE)
   public ResponseEntity<ResponseDto<Void>> matchApprove(
           @AuthenticationPrincipal UserPrincipal userPrincipal,
           @PathVariable Long matchWaitingListId,
           @RequestBody PutApproveMatchWaitingListRequestDto dto
           ){
       Long userId = userPrincipal.getId();

       return ResponseDto.toResponseEntity(HttpStatus.OK, matchWaitingListService.matchApprove(userId, matchWaitingListId, dto));
   }

   @PreAuthorize("hasRole('TRAINER')")
   @PutMapping(REJECT)
   public ResponseEntity<ResponseDto<Void>> matchReject(
           @AuthenticationPrincipal UserPrincipal userPrincipal,
           @PathVariable Long matchWaitingListId,
           @RequestBody PutRejectMatchWaitingListRequestDto dto
           ) {
       Long userId = userPrincipal.getId();

       return ResponseDto.toResponseEntity(HttpStatus.OK, matchWaitingListService.matchReject(userId, matchWaitingListId,dto));

   }
}
