package com.bcl.fitmate.backend.dto.comment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class GetCommentResponseDto {
    private Long id;
    private String commenterUsername;
    private String commenterName;
    private String commenterProfileImageUrl;
    private String content;
    private String createdAt;
}
