package com.bcl.fitmate.backend.dto.trainer.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class TrainerInfoRequestDto {
    private Long id;

    @NotBlank(message = "근무지 주소는 필수 항목입니다.")
    private String jobAddress;

    @NotBlank(message = "짧은 자기소개는 필수 항목입니다.")
    private String shortIntroduce;

    @NotBlank(message = "긴 자기소개는 필수 항목입니다.")
    private String longIntroduce;

    private List<MultipartFile> files;

    @NotBlank(message = "학교명은 필수 항목입니다.")
    private String educationName;

    @NotBlank(message = "입학년도는 필수 항목입니다.")
    private String educationEntrance;

    @NotBlank(message = "졸업년도는 필수 항목입니다.")
    private String educationGraduate;
}
