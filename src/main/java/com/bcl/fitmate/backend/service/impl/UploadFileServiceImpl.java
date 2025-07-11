package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ResponseMessage;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UploadFileServiceImpl implements UploadFileService {
    private static final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);
    private final UploadFileRepository uploadFileRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;


    @Override
    @Transactional
    public List<FileResponseDto> uploadMultiFiles(List<MultipartFile> files, Long targetId, TargetType targetType) {
        List<UploadFile> savedEntities = new ArrayList<>();

        File dir = new File(uploadDir);
        if(!dir.exists()) {
            boolean created = dir.mkdirs();
            if(!created) {
                throw new IllegalStateException("파일 디렉토리 생성 실패");
            }
        }

        for(MultipartFile file: files) {

            try {
                String original = file.getOriginalFilename();
                String uuidName = UUID.randomUUID() + "_" + original;
                String fullPath = uploadDir + "/" + uuidName;

                file.transferTo(new File(fullPath));
                String webPath = "/files/" + uuidName;

                UploadFile uploadFile = UploadFile.builder()
                        .originalName(original)
                        .fileName(uuidName)
                        .filePath(webPath)
                        .fileSize(file.getSize())
                        .fileType(file.getContentType())
                        .targetId(targetId)
                        .targetType(targetType)
                        .build();

                savedEntities.add(uploadFile);
            } catch (IOException e) {
                throw new IllegalStateException("파일 저장 중 오류 발생", e);
            }
        }

        List<UploadFile> saved = uploadFileRepository.saveAll(savedEntities);

        return saved.stream()
                .map(FileResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FileResponseDto> getMultiFiles(Long targetId, TargetType targetType) {
        List<UploadFile> files = uploadFileRepository.findAllByTargetIdAndTargetType(targetId, targetType);
        return files.stream()
                .map(FileResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public FileResponseDto getSingleMultiFile(Long fileId) {
        UploadFile file = uploadFileRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.FILE_NOT_ATTACHED));
        return FileResponseDto.fromEntity(file);
    }

    @Override
    public void deleteFile(Long fileId)  {
        UploadFile file = uploadFileRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.FILE_NOT_ATTACHED));

        File physicalFile = new File(file.getFilePath() + file.getFileName());
        if(physicalFile.exists()) {
            physicalFile.delete();
        }
        uploadFileRepository.deleteById(fileId);
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
