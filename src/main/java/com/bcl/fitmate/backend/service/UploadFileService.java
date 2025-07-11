package com.bcl.fitmate.backend.service;

import com.bcl.fitmate.backend.common.enums.uploadFile.TargetType;
import com.bcl.fitmate.backend.dto.FileResponseDto;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.entity.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UploadFileService {
    List<FileResponseDto> uploadMultiFiles(List<MultipartFile> files, Long targetId, TargetType targetType);
    List<FileResponseDto> getMultiFiles(Long targetId, TargetType targetType);
    FileResponseDto getSingleMultiFile(Long fileId);
    void deleteFile(Long fileId);
    UploadFile saveSingleFile(MultipartFile file, Long targetId, TargetType targetType) throws IOException;
    ResponseDto<FileResponseDto> updateSingleFile(Long fileId, MultipartFile newFile) throws IOException;
}
