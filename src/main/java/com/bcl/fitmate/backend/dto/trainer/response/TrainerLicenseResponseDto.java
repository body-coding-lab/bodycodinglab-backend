package com.bcl.fitmate.backend.dto.trainer.response;

import com.bcl.fitmate.backend.common.enums.trainer.LicenseType;
import com.bcl.fitmate.backend.dto.FileResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class TrainerLicenseResponseDto {
    private Long id;
    private Long trainerId;
    private LicenseType licenseType;
    private String licenseName;
    private List<FileResponseDto> fileNames;
}
