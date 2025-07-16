package com.bcl.fitmate.backend.controller;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.common.enums.board.Category;
import com.bcl.fitmate.backend.config.security.UserPrincipal;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.board.request.BoardRequestDto;
import com.bcl.fitmate.backend.dto.board.response.BoardDetailResponseDto;
import com.bcl.fitmate.backend.dto.board.response.BoardListResponseDto;
import com.bcl.fitmate.backend.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiMappingPattern.MATCH_API)
public class BoardController {
    private final BoardService boardService;

    private static final String CREATE_POST = "/{matchId}";
    private static final String UPDATE_POST = "/{matchId}/posts/{postId}";
    private static final String DELETE_POST = "/{matchId}/posts/{postId}";
    private static final String GET_POST_DETAIL = "/{matchId}/posts/{postId}";
    private static final String GET_POST_LIST = "/{matchId}/posts";
    private static final String SEARCH_POST_BY_NAME = "/{matchId}/search-name";
    private static final String SEARCH_POST_BY_TITLE = "/{matchId}/search-title";
    private static final String SEARCH_POST_BY_CONTENT = "/{matchId}/search-content";

    @PostMapping(CREATE_POST)
    public ResponseEntity<ResponseDto<BoardDetailResponseDto>> createPost(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long matchId,
            @Valid @RequestPart(value = "dto") BoardRequestDto dto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ){
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, boardService.createPost(id, matchId, dto, files));
    }

    @PutMapping(UPDATE_POST)
    public ResponseEntity<ResponseDto<BoardDetailResponseDto>> updatePost(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long matchId,
            @PathVariable Long postId,
            @Valid @RequestPart(value = "dto") BoardRequestDto dto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ){
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, boardService.updatePost(id, matchId, postId, dto, files));
    }

    @DeleteMapping(DELETE_POST)
    public ResponseEntity<ResponseDto<Void>> deletePost(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long matchId,
            @PathVariable Long postId
    ){
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, boardService.deletePost(id, matchId, postId));
    }

    @GetMapping(GET_POST_DETAIL)
    public ResponseEntity<ResponseDto<BoardDetailResponseDto>> getPost(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long matchId,
            @PathVariable Long postId
    ){
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, boardService.getPost(id, matchId, postId));
    }

    @GetMapping(GET_POST_LIST)
    public ResponseEntity<ResponseDto<Page<BoardListResponseDto>>> getPostList(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long matchId,
            @RequestParam Category category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, boardService.getPostList(id, matchId, category, page, size));
    }

    @GetMapping(SEARCH_POST_BY_NAME)
    public ResponseEntity<ResponseDto<Page<BoardListResponseDto>>> searchPostByName(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long matchId,
            @RequestParam Category category,
            @RequestParam(required = false) String writerName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, boardService.searchPostByName(id, matchId, category, writerName, page, size));
    }

    @GetMapping(SEARCH_POST_BY_TITLE)
    public ResponseEntity<ResponseDto<Page<BoardListResponseDto>>> searchPostByTitle(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long matchId,
            @RequestParam Category category,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, boardService.searchPostByTitle(id, matchId, category, title, page, size));
    }

    @GetMapping(SEARCH_POST_BY_CONTENT)
    public ResponseEntity<ResponseDto<Page<BoardListResponseDto>>> searchPostByContent(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long matchId,
            @RequestParam Category category,
            @RequestParam(required = false) String content,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Long id = userPrincipal.getId();
        return ResponseDto.toResponseEntity(HttpStatus.OK, boardService.searchPostByContent(id, matchId, category, content, page, size));
    }
}
