package com.bcl.fitmate.backend.dto.comment.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetCommentResponseDto {
    private String commenterUsername;
    private String commenterName;
    private String commenterProfileImageUrl;
    private String content;
    private String createdAt;
}
