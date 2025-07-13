package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.common.enums.board.Category;
import com.bcl.fitmate.backend.common.enums.uploadFile.TargetType;
import com.bcl.fitmate.backend.common.util.DateUtils;
import com.bcl.fitmate.backend.dto.comment.response.GetCommentResponseDto;
import com.bcl.fitmate.backend.dto.uploadFile.response.FileResponseDto;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.board.request.BoardRequestDto;
import com.bcl.fitmate.backend.dto.board.response.BoardDetailResponseDto;
import com.bcl.fitmate.backend.dto.board.response.BoardListResponseDto;
import com.bcl.fitmate.backend.entity.*;
import com.bcl.fitmate.backend.repository.BoardRepository;
import com.bcl.fitmate.backend.repository.MatchRepository;
import com.bcl.fitmate.backend.service.BoardService;
import com.bcl.fitmate.backend.service.UploadFileService;
import com.bcl.fitmate.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final UserService userService;
    private final MatchRepository matchRepository;
    private final UploadFileService uploadFileService;

    @Override
    @Transactional
    public ResponseDto<BoardDetailResponseDto> createPost(Long id, Long matchId, BoardRequestDto dto, List<MultipartFile> files) {
        BoardDetailResponseDto data = null;

        User user = userService.getUserById(id);

        Match match = matchRepository.findById(matchId)
                .orElse(null);

        if(match == null) {
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH, ResponseMessage.NOT_EXISTS_MATCH);
        }

        boolean isTrainer = match.getTrainer().getId().equals(id);
        boolean isMember = match.getMember().getId().equals(id);

        if(!isTrainer && !isMember) {
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH, ResponseMessage.NOT_EXISTS_MATCH);
        }

        Board board = Board.builder()
                .category(dto.getCategory())
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
        boardRepository.save(board);

        List<FileResponseDto> uploadedImages = Collections.emptyList();

        if(files != null && !files.isEmpty()) {
            List<MultipartFile> nonEmptyFiles = files.stream()
                    .filter(file -> !file.isEmpty())
                    .collect(Collectors.toList());

            if(!nonEmptyFiles.isEmpty()) {
                uploadFileService.uploadMultiFiles(nonEmptyFiles, board.getId(), TargetType.BOARD);
            }
        }

        data = BoardDetailResponseDto.builder()
                .boardId(board.getId())
                .matchId(match.getId())
                .writerId(user.getId())
                .writerName(user.getName())
                .category(board.getCategory())
                .title(board.getTitle())
                .content(board.getContent())
                .viewCount(board.getViewCount())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .boardImages(uploadedImages)
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    @Transactional
    public ResponseDto<BoardDetailResponseDto> updatePost(Long id, Long matchId, Long postId, BoardRequestDto dto, List<MultipartFile> files) {
        BoardDetailResponseDto data = null;

        User user = userService.getUserById(id);

        Match match = matchRepository.findById(matchId)
                .orElse(null);

        if(match == null) {
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH, ResponseMessage.NOT_EXISTS_MATCH);
        }

        boolean isTrainer = match.getTrainer().getId().equals(id);
        boolean isMember = match.getMember().getId().equals(id);

        if(!isTrainer && !isMember) {
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH, ResponseMessage.NOT_EXISTS_MATCH);
        }

        Board board = boardRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_POST));

        if(!board.getMatch().getId().equals(matchId)) {
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH, ResponseMessage.NOT_EXISTS_MATCH);
        }

        board.setCategory(dto.getCategory());
        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());

        Board updatedBoard = boardRepository.save(board);

        List<FileResponseDto> uploadedImages = Collections.emptyList();

        if(files != null && !files.isEmpty()) {
            List<MultipartFile> nonEmptyFiles = files.stream()
                    .filter(file -> !file.isEmpty())
                    .collect(Collectors.toList());

            if(!nonEmptyFiles.isEmpty()) {
                uploadFileService.uploadMultiFiles(nonEmptyFiles, board.getId(), TargetType.BOARD);
            }
        }

        data = BoardDetailResponseDto.builder()
                .boardId(updatedBoard.getId())
                .matchId(updatedBoard.getMatch().getId())
                .writerId(user.getId())
                .writerName(updatedBoard.getWriter().getName())
                .category(updatedBoard.getCategory())
                .title(updatedBoard.getTitle())
                .content(updatedBoard.getContent())
                .viewCount(updatedBoard.getViewCount())
                .createdAt(updatedBoard.getCreatedAt())
                .updatedAt(updatedBoard.getUpdatedAt())
                .boardImages(uploadedImages)
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    @Transactional
    public ResponseDto<Void> deletePost(Long id, Long matchId, Long postId) {
        Match match = matchRepository.findById(matchId)
                .orElse(null);

        if(match == null) {
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH, ResponseMessage.NOT_EXISTS_MATCH);
        }

        boolean isTrainer = match.getTrainer().getId().equals(id);
        boolean isMember = match.getMember().getId().equals(id);

        if(!isTrainer && !isMember) {
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH, ResponseMessage.NOT_EXISTS_MATCH);
        }

        Board board = boardRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_POST));

        if(!board.getMatch().getId().equals(matchId)) {
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH, ResponseMessage.NOT_EXISTS_MATCH);
        }

        boardRepository.delete(board);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, null);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<BoardDetailResponseDto> getPost(Long id, Long matchId, Long postId) {
        Match match = matchRepository.findById(matchId)
                .orElse(null);

        if(match == null) {
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH, ResponseMessage.NOT_EXISTS_MATCH);
        }

        boolean isTrainer = match.getTrainer().getId().equals(id);
        boolean isMember = match.getMember().getId().equals(id);

        if(!isTrainer && !isMember) {
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH, ResponseMessage.NOT_EXISTS_MATCH);
        }

        Board board = boardRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_POST));

        if(!board.getMatch().getId().equals(matchId)) {
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH, ResponseMessage.NOT_EXISTS_MATCH);
        }

        board.increaseViewCount();

        List<FileResponseDto> boardImages = uploadFileService.getMultiFiles(board.getId(), TargetType.BOARD);
        List<GetCommentResponseDto> comments = board.getComments().stream()
                .map(this::toGetCommentResponseDto)
                .collect(Collectors.toList());

        BoardDetailResponseDto data = BoardDetailResponseDto.builder()
                .boardId(board.getId())
                .matchId(board.getMatch().getId())
                .writerId(board.getWriter().getId())
                .writerName(board.getWriter().getName())
                .category(board.getCategory())
                .title(board.getTitle())
                .content(board.getContent())
                .viewCount(board.getViewCount())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .boardImages(boardImages)
                .comments(comments)
                .build();
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<Page<BoardListResponseDto>> getPostList(Long id, Long matchId, Category category, int page, int size) {
        Match match = matchRepository.findById(matchId)
                .orElse(null);

        if(match == null) {
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH, ResponseMessage.NOT_EXISTS_MATCH);
        }

        boolean isTrainer = match.getTrainer().getId().equals(id);
        boolean isMember = match.getMember().getId().equals(id);

        if(!isTrainer && !isMember) {
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH, ResponseMessage.NOT_EXISTS_MATCH);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Board> boards = boardRepository.findAllByMatchIdAndCategory(matchId, category, pageable);

        Page<BoardListResponseDto> data = boards.map(board -> BoardListResponseDto.builder()
                .boardId(board.getId())
                .matchId(board.getMatch().getId())
                .category(board.getCategory())
                .title(board.getTitle())
                .writerName(board.getWriter().getName())
                .viewCount(board.getViewCount())
                .createdAt(board.getCreatedAt())
                .build());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<Page<BoardListResponseDto>> searchPostByName(Long id, Long matchId, Category category, String writerName, int page, int size) {
        Match match = matchRepository.findById(matchId)
                .orElse(null);

        if(match == null) {
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH, ResponseMessage.NOT_EXISTS_MATCH);
        }

        boolean isTrainer = match.getTrainer().getId().equals(id);
        boolean isMember = match.getMember().getId().equals(id);

        if(!isTrainer && !isMember) {
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH, ResponseMessage.NOT_EXISTS_MATCH);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Board> boards = boardRepository.searchByWriterName(matchId, category, writerName, pageable);

        Page<BoardListResponseDto> data = boards.map(board -> BoardListResponseDto.builder()
                .boardId(board.getId())
                .matchId(board.getMatch().getId())
                .category(board.getCategory())
                .title(board.getTitle())
                .writerName(board.getWriter().getName())
                .viewCount(board.getViewCount())
                .createdAt(board.getCreatedAt())
                .build());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<Page<BoardListResponseDto>> searchPostByTitle(Long id, Long matchId, Category category, String title, int page, int size) {
        Match match = matchRepository.findById(matchId)
                .orElse(null);

        if(match == null) {
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH, ResponseMessage.NOT_EXISTS_MATCH);
        }

        boolean isTrainer = match.getTrainer().getId().equals(id);
        boolean isMember = match.getMember().getId().equals(id);

        if(!isTrainer && !isMember) {
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH, ResponseMessage.NOT_EXISTS_MATCH);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Board> boards = boardRepository.searchByTitle(matchId, category, title, pageable);

        Page<BoardListResponseDto> data = boards.map(board -> BoardListResponseDto.builder()
                .boardId(board.getId())
                .matchId(board.getMatch().getId())
                .category(board.getCategory())
                .title(board.getTitle())
                .writerName(board.getWriter().getName())
                .viewCount(board.getViewCount())
                .createdAt(board.getCreatedAt())
                .build());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<Page<BoardListResponseDto>> searchPostByContent(Long id, Long matchId, Category category, String content, int page, int size) {
        Match match = matchRepository.findById(matchId)
                .orElse(null);

        if(match == null) {
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH, ResponseMessage.NOT_EXISTS_MATCH);
        }

        boolean isTrainer = match.getTrainer().getId().equals(id);
        boolean isMember = match.getMember().getId().equals(id);

        if(!isTrainer && !isMember) {
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH, ResponseMessage.NOT_EXISTS_MATCH);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Board> boards = boardRepository.searchByContent(matchId, category, content, pageable);

        Page<BoardListResponseDto> data = boards.map(board -> BoardListResponseDto.builder()
                .boardId(board.getId())
                .matchId(board.getMatch().getId())
                .category(board.getCategory())
                .title(board.getTitle())
                .writerName(board.getWriter().getName())
                .viewCount(board.getViewCount())
                .createdAt(board.getCreatedAt())
                .build());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data);
    }

    @Override
    @Transactional(readOnly = true)
    public Board getBoardById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_POST));
    }

    private GetCommentResponseDto toGetCommentResponseDto(Comment comment) {
        User commenter = comment.getCommenter();
        String profileImageUrl = null;
        UploadFile profileImage = commenter.getProfileImage();
        if (profileImage != null) {
            profileImageUrl = ApiMappingPattern.FILE_API + "/single/" + profileImage.getId();
        }

        return GetCommentResponseDto.builder()
                .id(comment.getId())
                .commenterUsername(commenter.getUsername())
                .commenterName(commenter.getName())
                .commenterProfileImageUrl(profileImageUrl)
                .content(comment.getContent())
                .createdAt(DateUtils.format(comment.getCreatedAt()))
                .build();
    }

}
