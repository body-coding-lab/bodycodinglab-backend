package com.bcl.fitmate.backend.dto.uploadFile.response;

import com.bcl.fitmate.backend.entity.UploadFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileResponseDto {
    private Long fileId;
    private String originalName;
    private String fileName;
    private String filePath;
    private String fileType;
    private Long fileSize;

    public static FileResponseDto fromEntity(UploadFile file) {
        return new FileResponseDto(
                file.getId(),
                file.getOriginalName(),
                file.getFileName(),
                file.getFilePath(),
                file.getFileType(),
                file.getFileSize()
        );
    }
}
