package com.bcl.fitmate.backend.service;

import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.comment.request.CommentRequestDto;
import com.bcl.fitmate.backend.entity.Comment;
import jakarta.validation.Valid;

public interface CommentService {
    ResponseDto<Void> createComment(Long id, Long matchId, Long postId, @Valid CommentRequestDto dto);
    ResponseDto<Void> updateComment(Long id, Long matchId, Long postId, Long commentId, @Valid CommentRequestDto dto);
    ResponseDto<Void> deleteComment(Long id, Long matchId, Long postId, Long commentId);
    Comment getComment(Long id);
}
