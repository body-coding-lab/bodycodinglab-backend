package com.bcl.fitmate.backend.dto.board.response;

import com.bcl.fitmate.backend.common.enums.board.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class BoardListResponseDto {
    private Long boardId;
    private Category category;
    private String title;
    private String content;
    private String writerName;
    private Long viewCount;
    private Long postLike;
    private LocalDateTime createdAt;
}
