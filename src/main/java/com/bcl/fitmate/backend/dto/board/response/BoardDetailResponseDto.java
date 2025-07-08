package com.bcl.fitmate.backend.dto.board.response;

import com.bcl.fitmate.backend.common.enums.board.Category;
import com.bcl.fitmate.backend.dto.FileResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BoardDetailResponseDto {
    private Long boardId;
    private Long matchId;
    private Long writerId;
    private Category category;
    private String title;
    private String content;
    private String writerName;
    private Long viewCount;
    private Long like;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<FileResponseDto> boardImages;
}
