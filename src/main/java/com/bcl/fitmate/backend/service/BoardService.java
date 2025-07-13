package com.bcl.fitmate.backend.service;

import com.bcl.fitmate.backend.common.enums.board.Category;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.board.request.BoardRequestDto;
import com.bcl.fitmate.backend.dto.board.response.BoardDetailResponseDto;
import com.bcl.fitmate.backend.dto.board.response.BoardListResponseDto;
import com.bcl.fitmate.backend.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {
    ResponseDto<BoardDetailResponseDto> createPost(Long id, Long matchId, BoardRequestDto dto, List<MultipartFile> files);
    ResponseDto<BoardDetailResponseDto> updatePost(Long id, Long matchId, Long postId, BoardRequestDto dto, List<MultipartFile> files);
    ResponseDto<Void> deletePost(Long id, Long matchId, Long postId);
    ResponseDto<BoardDetailResponseDto> getPost(Long id, Long matchId, Long postId);
    ResponseDto<Page<BoardListResponseDto>> getPostList(Long id, Long matchId, Category category, int page, int size);
    ResponseDto<Page<BoardListResponseDto>> searchPostByName(Long id, Long matchId, Category category, String writerName, int page, int size);
    ResponseDto<Page<BoardListResponseDto>> searchPostByTitle(Long id, Long matchId, Category category, String title, int page, int size);
    ResponseDto<Page<BoardListResponseDto>> searchPostByContent(Long id, Long matchId, Category category, String content, int page, int size);
    Board getBoardById(Long id);
}
