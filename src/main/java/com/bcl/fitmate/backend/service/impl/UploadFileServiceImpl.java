package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.enums.uploadFile.TargetType;
import com.bcl.fitmate.backend.dto.FileResponseDto;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.entity.UploadFile;
import com.bcl.fitmate.backend.repository.UploadFileRepository;
import com.bcl.fitmate.backend.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UploadFileServiceImpl implements UploadFileService {
    private static final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);
    private final UploadFileRepository uploadFileRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;


    @Override
    public List<FileResponseDto> uploadMultiFiles(List<MultipartFile> files, Long targetId, TargetType targetType) {
        return List.of();
    }

    @Override
    public List<FileResponseDto> getMultiFiles(Long targetId, TargetType targetType) {
        return List.of();
    }

    @Override
    public FileResponseDto getSingleMultiFile(Long fileId) {
        return null;
    }

    @Override
    public Void deleteMultiFile(Long fileId) {
        return null;
    }

    @Override
    public UploadFile saveSingleFile(MultipartFile file, Long targetId, TargetType targetType) throws IOException {
        try {
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String original = file.getOriginalFilename();
            String uuidName = UUID.randomUUID() + "_" + original;
            String fullPath = uploadDir + "/" + uuidName;
            file.transferTo(new File(fullPath));

            UploadFile uf = UploadFile.builder()
                    .originalName(original)
                    .fileName(uuidName)
                    .filePath(uploadDir + "/")
                    .fileSize(file.getSize())
                    .fileType(file.getContentType())
                    .targetId(targetId)
                    .targetType(targetType)
                    .build();
            uploadFileRepository.save(uf);

            return uf;
        } catch (IOException e) {
            log.error("단건 파일 저장 예외 발생: {}", e.getMessage(), e);

            return null;
        }
    }

    @Override
    @Transactional
    public ResponseDto<FileResponseDto> updateSingleFile(Long fileId, MultipartFile newFile) {
        UploadFile existing = uploadFileRepository.findById(fileId).orElse(null);
//        if (existing == null) {
//            return saveSingleFile(newFile, )
//        }

        return null;
    }
}
