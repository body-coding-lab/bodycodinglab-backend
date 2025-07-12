package com.bcl.fitmate.backend.service;

import com.bcl.fitmate.backend.common.enums.uploadFile.TargetType;
import com.bcl.fitmate.backend.dto.uploadFile.response.FileResponseDto;
import com.bcl.fitmate.backend.dto.uploadFile.response.SingleFileResponseDto;
import com.bcl.fitmate.backend.entity.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface UploadFileService {
    UploadFile saveSingleFile(MultipartFile file, Long targetId, TargetType targetType) throws IOException;
    UploadFile updateSingleFile(Long fileId, Long targetId, TargetType targetType, MultipartFile newFile) throws IOException;
    SingleFileResponseDto getSingleFile(Long fileId) throws FileNotFoundException;
    List<FileResponseDto> uploadMultiFiles(List<MultipartFile> files, Long targetId, TargetType targetType);
    List<FileResponseDto> getMultiFiles(Long targetId, TargetType targetType);
    FileResponseDto getSingleMultiFile(Long fileId);
    void deleteFile(Long fileId);
}
