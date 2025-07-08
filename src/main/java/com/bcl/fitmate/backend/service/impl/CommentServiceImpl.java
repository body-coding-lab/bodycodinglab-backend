package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.comment.request.CommentRequestDto;
import com.bcl.fitmate.backend.dto.comment.response.GetCommentResponseDto;
import com.bcl.fitmate.backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    @Override
    public ResponseDto<List<GetCommentResponseDto>> getComments(Long boardId) {
        return null;
    }

    @Override
    public ResponseDto<Void> createComment(Long id, Long boardId, CommentRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<Void> updateComment(Long id, Long boardId, Long commentId, CommentRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto<Void> deleteComment(Long id, Long boardId, Long commentId) {
        return null;
    }
}
