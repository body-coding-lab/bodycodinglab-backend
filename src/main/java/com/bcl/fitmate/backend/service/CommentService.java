package com.bcl.fitmate.backend.service;

import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.comment.request.CommentRequestDto;
import com.bcl.fitmate.backend.dto.comment.response.GetCommentResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface CommentService {
    ResponseDto<List<GetCommentResponseDto>> getComments(Long boardId);
    ResponseDto<Void> createComment(Long id, Long boardId, @Valid CommentRequestDto dto);
    ResponseDto<Void> updateComment(Long id, Long boardId, Long commentId, @Valid CommentRequestDto dto);
    ResponseDto<Void> deleteComment(Long id, Long boardId, Long commentId);
}
