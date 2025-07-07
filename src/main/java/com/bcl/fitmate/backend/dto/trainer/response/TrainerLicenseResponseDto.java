package com.bcl.fitmate.backend.dto.trainer.response;

import com.bcl.fitmate.backend.common.enums.trainer.LicenseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TrainerLicenseResponseDto {
    private Long id;
    private Long trainerId;
    private LicenseType licenseType;
    private String licenseName;
}
