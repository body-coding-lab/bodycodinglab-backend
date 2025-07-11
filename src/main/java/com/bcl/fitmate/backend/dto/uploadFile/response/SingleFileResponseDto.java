package com.bcl.fitmate.backend.dto.uploadFile.response;

import com.bcl.fitmate.backend.common.enums.uploadFile.TargetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.core.io.Resource;

@Getter
@AllArgsConstructor
@Builder
public class SingleFileResponseDto {
    private String fileType;
    private TargetType targetType;
    private Resource resource;
    private String encodedFileName;
}
