package com.bcl.fitmate.backend.dto.board.response;

import com.bcl.fitmate.backend.common.enums.board.Category;
import com.bcl.fitmate.backend.dto.comment.response.GetCommentResponseDto;
import com.bcl.fitmate.backend.dto.uploadFile.response.FileResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BoardDetailResponseDto {
    private Long boardId;
    private Long matchId;
    private Long writerId;
    private String writerName;
    private Category category;
    private String title;
    private String content;
    private Long viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<FileResponseDto> boardImages;
    private List<GetCommentResponseDto> comments;
}
