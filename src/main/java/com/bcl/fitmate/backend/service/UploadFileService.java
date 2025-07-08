package com.bcl.fitmate.backend.service;

import com.bcl.fitmate.backend.common.enums.uploadFile.TargetType;
import com.bcl.fitmate.backend.dto.FileResponseDto;
import com.bcl.fitmate.backend.dto.ResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadFileService {
    ResponseDto<List<FileResponseDto>> uploadMultiFiles(List<MultipartFile> files, Long targetId, TargetType targetType);

    ResponseDto<List<FileResponseDto>> getMultiFiles(Long targetId, TargetType targetType);

    ResponseDto<FileResponseDto> getSingleMultiFile(Long fileId);

    ResponseDto<Void> deleteMultiFile(Long fileId);
}
