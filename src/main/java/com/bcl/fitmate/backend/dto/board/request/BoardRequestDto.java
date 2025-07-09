package com.bcl.fitmate.backend.dto.board.request;

import com.bcl.fitmate.backend.common.enums.board.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BoardRequestDto {
    @NotBlank(message = "카테고리는 필수 항목입니다.")
    private Category category;

    @NotBlank(message = "제목은 필수 항목입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 항목입니다.")
    private String content;

    private List<MultipartFile> files;
}
