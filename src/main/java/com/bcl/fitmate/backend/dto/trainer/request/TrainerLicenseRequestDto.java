package com.bcl.fitmate.backend.dto.trainer.request;

import com.bcl.fitmate.backend.common.enums.trainer.LicenseType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class TrainerLicenseRequestDto {
    @NotBlank(message = "자격증 종류는 필수 항목입니다.")
    private LicenseType licenseType;

    @NotBlank(message = "자격증 이름은 필수 항목입니다.")
    private String licenseName;

    private List<MultipartFile> files;
}
