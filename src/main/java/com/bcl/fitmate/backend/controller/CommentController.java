package com.bcl.fitmate.backend.controller;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.config.security.UserPrincipal;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.comment.request.CommentRequestDto;
import com.bcl.fitmate.backend.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiMappingPattern.MATCH_API)
public class CommentController {
    private final CommentService commentService;

    private static final String BOARD_COMMENTS = "/{boardId}/comments";
    private static final String BOARD_COMMENT_DETAIL = BOARD_COMMENTS + "/{commentId}";

    @PreAuthorize("hasAnyRole('MEMBER', 'TRAINER')")
    @PostMapping(BOARD_COMMENTS)
    public ResponseEntity<ResponseDto<Void>> createComment(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long boardId,
            @Valid @RequestBody CommentRequestDto dto
    ) {
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, commentService.createComment(id, boardId, dto));
    }

    @PreAuthorize("hasAnyRole('MEMBER', 'TRAINER')")
    @PutMapping(BOARD_COMMENT_DETAIL)
    public ResponseEntity<ResponseDto<Void>> updateComment(
        @AuthenticationPrincipal UserPrincipal userPrincipal,
        @PathVariable Long boardId,
        @PathVariable Long commentId,
        @Valid @RequestBody CommentRequestDto dto
    ) {
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, commentService.updateComment(id, boardId, commentId, dto));
    }

    @PreAuthorize("hasAnyRole('MEMBER', 'TRAINER')")
    @DeleteMapping(BOARD_COMMENT_DETAIL)
    public ResponseEntity<ResponseDto<Void>> deleteComment(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long boardId,
            @PathVariable Long commentId
    ) {
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, commentService.deleteComment(id, boardId, commentId));
    }
}
