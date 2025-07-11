package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.common.enums.uploadFile.TargetType;
import com.bcl.fitmate.backend.dto.uploadFile.response.FileResponseDto;
import com.bcl.fitmate.backend.dto.uploadFile.response.SingleFileResponseDto;
import com.bcl.fitmate.backend.entity.UploadFile;
import com.bcl.fitmate.backend.repository.UploadFileRepository;
import com.bcl.fitmate.backend.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    @Transactional
    public UploadFile saveSingleFile(MultipartFile file, Long targetId, TargetType targetType) {
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
    public UploadFile updateSingleFile(Long fileId, Long targetId, TargetType targetType, MultipartFile newFile) {
        try {
            UploadFile existing = uploadFileRepository.findById(fileId).orElse(null);
            if (existing == null) {
                return saveSingleFile(newFile, targetId, targetType);
            }

            Path oldPath = Paths.get(existing.getFilePath() + existing.getFileName());
            Files.deleteIfExists(oldPath);

            String newFileName = UUID.randomUUID() + "_" + newFile.getOriginalFilename();
            String savePath = existing.getFilePath();
            Files.copy(newFile.getInputStream(), Paths.get(savePath, newFileName));

            existing.updateFile(
                    newFile.getOriginalFilename(),
                    newFileName,
                    newFile.getContentType(),
                    newFile.getSize()
            );

            return existing;
        } catch (IOException e) {
            log.error("단건 파일 수정 예외 발생: {}", e.getMessage(), e);

            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public SingleFileResponseDto getSingleFile(Long fileId) throws FileNotFoundException {
        UploadFile uf = uploadFileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("파일ID를 찾을 수 없습니다. ID: " + fileId));

        Path path = Paths.get(uf.getFilePath(), uf.getFileName());
        Resource resource = new FileSystemResource(path);

        if (!resource.exists()) {
            throw new FileNotFoundException("파일이 존재하지 않습니다. path: " + path.toString());
        }

        String encodedFileName = URLEncoder.encode(uf.getOriginalName(), StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");

        return SingleFileResponseDto.builder()
                .fileType(uf.getFileType())
                .targetType(uf.getTargetType())
                .encodedFileName(encodedFileName)
                .resource(resource)
                .build();
    }
}
