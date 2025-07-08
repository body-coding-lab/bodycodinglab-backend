package com.bcl.fitmate.backend.dto.comment.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentRequestDto {
    @NotBlank(message = "댓글 내용은 필수 항목입니다.")
    private String content;
}
