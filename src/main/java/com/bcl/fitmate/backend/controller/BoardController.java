package com.bcl.fitmate.backend.controller;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.common.enums.board.Category;
import com.bcl.fitmate.backend.config.security.UserPrincipal;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.board.request.BoardRequestDto;
import com.bcl.fitmate.backend.dto.board.response.BoardDetailResponseDto;
import com.bcl.fitmate.backend.dto.board.response.BoardListResponseDto;
import com.bcl.fitmate.backend.dto.trainer.response.TrainerListResponseDto;
import com.bcl.fitmate.backend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiMappingPattern.BOARD_API)
public class BoardController {
    private final BoardService boardService;

    private static final String CREATE_POST = "/{matchId}";
    private static final String UPDATE_POST = "/{matchId}";
    private static final String DELETE_POST = "/{matchId}/posts/{postId}";
    private static final String GET_POST_DETAIL = "/{matchId}/posts/{postId}";
    private static final String GET_POST_LIST = "/{matchId}/posts";
    private static final String SEARCH_POST = "/{matchId}/search";

    @PostMapping(CREATE_POST)
    public ResponseEntity<ResponseDto<BoardDetailResponseDto>> createPost(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long matchId,
            @ModelAttribute BoardRequestDto dto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ){
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, boardService.createPost(id, matchId, dto, files));
    }

    @PutMapping(UPDATE_POST)
    public ResponseEntity<ResponseDto<BoardDetailResponseDto>> updatePost(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long matchId,
            @ModelAttribute BoardRequestDto dto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ){
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, boardService.updatePost(id, matchId, dto, files));
    }

    @DeleteMapping(DELETE_POST)
    public ResponseEntity<ResponseDto<Void>> deletePost(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long matchId,
            @PathVariable Long postId
    ){
        Long id = userPrincipal.getId();
        boardService.deletePost(id, matchId, postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(GET_POST_DETAIL)
    public ResponseEntity<ResponseDto<BoardDetailResponseDto>> getPost(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long matchId,
            @PathVariable Long postId
    ){
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, boardService.getPost(id, matchId));
    }

    @GetMapping(GET_POST_LIST)
    public ResponseEntity<ResponseDto<Page<BoardListResponseDto>>> getPostList(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long matchId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, boardService.getPostList(id, matchId, page, size));
    }

    @GetMapping(SEARCH_POST)
    public ResponseEntity<ResponseDto<Page<BoardListResponseDto>>> searchPost(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long matchId,
            @RequestParam Category category,
            @RequestParam(required = false) String writerName,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, boardService.searchPost(id, matchId, category, writerName, title, content, page, size));
    }

    // 대표 이미지가 없는 경우 null설정
    // 검색 null 설정
}
