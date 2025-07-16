package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.comment.request.CommentRequestDto;
import com.bcl.fitmate.backend.entity.Board;
import com.bcl.fitmate.backend.entity.Comment;
import com.bcl.fitmate.backend.entity.Match;
import com.bcl.fitmate.backend.entity.User;
import com.bcl.fitmate.backend.repository.CommentRepository;
import com.bcl.fitmate.backend.service.BoardService;
import com.bcl.fitmate.backend.service.CommentService;
import com.bcl.fitmate.backend.service.MatchService;
import com.bcl.fitmate.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final BoardService boardService;

    @Override
    @Transactional
    public ResponseDto<Void> createComment(Long id, Long matchId, Long postId, CommentRequestDto dto) {
        User user = userService.getUserById(id);
        Board board = boardService.getBoardByMatchId(matchId);

        Comment comment = Comment.builder()
                .board(board)
                .commenter(user)
                .content(dto.getContent())
                .build();

        commentRepository.save(comment);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    @Transactional
    public ResponseDto<Void> updateComment(Long id, Long matchId, Long postId, Long commentId, CommentRequestDto dto) {
        User user = userService.getUserById(id);
        Board board = boardService.getBoardByMatchId(matchId);
        Comment comment = getComment(commentId);

        if (!user.getId().equals(comment.getCommenter().getId())) {
            return ResponseDto.fail(ResponseCode.NOT_COMMENT_OWNER, ResponseMessage.NOT_COMMENT_OWNER);
        }

        if (!board.getId().equals(comment.getBoard().getId())) {
            return ResponseDto.fail(ResponseCode.COMMENT_NOT_BELONG_POST, ResponseMessage.COMMENT_NOT_BELONG_POST);
        }

        comment.setContent(dto.getContent());
        commentRepository.save(comment);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    @Transactional
    public ResponseDto<Void> deleteComment(Long id, Long matchId, Long postId, Long commentId) {
        User user = userService.getUserById(id);
        Board board = boardService.getBoardByMatchId(matchId);
        Comment comment = getComment(commentId);

        if (!user.getId().equals(comment.getCommenter().getId())) {
            return ResponseDto.fail(ResponseCode.NOT_COMMENT_OWNER, ResponseMessage.NOT_COMMENT_OWNER);
        }

        if (!board.getId().equals(comment.getBoard().getId())) {
            return ResponseDto.fail(ResponseCode.COMMENT_NOT_BELONG_POST, ResponseMessage.COMMENT_NOT_BELONG_POST);
        }

        commentRepository.delete(comment);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_COMMENT));
    }
}
