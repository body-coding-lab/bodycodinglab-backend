package com.bcl.fitmate.backend.service;

import com.bcl.fitmate.backend.common.enums.board.Category;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.board.request.BoardRequestDto;
import com.bcl.fitmate.backend.dto.board.response.BoardDetailResponseDto;
import com.bcl.fitmate.backend.dto.board.response.BoardListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {
    ResponseDto<BoardDetailResponseDto> createPost(Long id, Long matchId, BoardRequestDto dto, List<MultipartFile> files);

    ResponseDto<BoardDetailResponseDto> updatePost(Long id, Long matchId, BoardRequestDto dto, List<MultipartFile> files);

    void deletePost(Long id, Long matchId, Long postId);

    ResponseDto<BoardDetailResponseDto> getPost(Long id, Long matchId);

    ResponseDto<Page<BoardListResponseDto>> getPostList(Long id, Long matchId, int page, int size);

    ResponseDto<Page<BoardListResponseDto>> searchPost(Long id, Long matchId, Category category, String writerName, String title, String content, int page, int size);
}
