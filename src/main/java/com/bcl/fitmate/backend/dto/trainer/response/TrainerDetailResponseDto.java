package com.bcl.fitmate.backend.dto.trainer.response;

import com.bcl.fitmate.backend.dto.FileResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class TrainerDetailResponseDto {
    private Long trainerId;
    private String name;
    private String jobAddress;
    private String shortIntroduce;
    private String longIntroduce;
    private String educationName;
    private String educationEntrance;
    private String educationGraduate;
    private List<TrainerCareerResponseDto> careers;
    private List<TrainerLicenseResponseDto> licenses;
    private String profileImage;
    private List<FileResponseDto> infoImages;
    private List<FileResponseDto> licenseImages;
}
