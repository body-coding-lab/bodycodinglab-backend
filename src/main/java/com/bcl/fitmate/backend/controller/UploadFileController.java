package com.bcl.fitmate.backend.controller;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.common.enums.uploadFile.TargetType;
import com.bcl.fitmate.backend.dto.uploadFile.response.FileResponseDto;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.uploadFile.response.SingleFileResponseDto;
import com.bcl.fitmate.backend.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiMappingPattern.FILE_API)
public class UploadFileController {
    private final UploadFileService uploadFileService;

    private static final String SINGLE_FILE = "/single/{fileId}";
    private static final String UPLOAD_MULTI_FILES = "/multi";
    private static final String GET_MULTI_FILES = "/multi";
    private static final String GET_SINGLE_MULTI_FILES = "/multi/{fileId}";
    private static final String DELETE_MULTI_FILES = "/{fileId}";

    @GetMapping(SINGLE_FILE)
    public ResponseEntity<Resource> getSingleFile(@PathVariable Long fileId) throws FileNotFoundException {
        SingleFileResponseDto dto = uploadFileService.getSingleFile(fileId);

        if (dto == null) {
            return ResponseEntity.notFound().build();
        }

        if (dto.getTargetType().equals(TargetType.PROFILE)) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(dto.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                    .body(dto.getResource());
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + dto.getEncodedFileName())
                .body(dto.getResource());
    }

    @PostMapping(UPLOAD_MULTI_FILES)
    public ResponseEntity<ResponseDto<List<FileResponseDto>>> uploadMultiFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("targetId") Long targetId,
            @RequestParam("targetType")TargetType targetType
    ) {
        uploadFileService.uploadMultiFiles(files, targetId, targetType);
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS));
    }

    @GetMapping(GET_MULTI_FILES)
    public ResponseEntity<ResponseDto<List<FileResponseDto>>> getMultiFiles(
            @RequestParam("targetId") Long targetId,
            @RequestParam("targetType")TargetType targetType
    ) {
        uploadFileService.getMultiFiles(targetId, targetType);
        return ResponseDto.toResponseEntity(HttpStatus.OK, ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS));
    }

    @GetMapping(GET_SINGLE_MULTI_FILES)
    public ResponseEntity<ResponseDto<FileResponseDto>> getSingleMultiFile(
            @PathVariable Long fileId
    ) {
        uploadFileService.getSingleMultiFile(fileId);
        return ResponseDto.toResponseEntity(HttpStatus.OK, ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS));
    }

    @DeleteMapping(DELETE_MULTI_FILES)
    public ResponseEntity<ResponseDto<Void>> deleteFile(
            @PathVariable Long fileId
    ) {
        uploadFileService.deleteFile(fileId);
        return ResponseDto.toResponseEntity(HttpStatus.OK, ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS));
    }
}
