package com.bcl.fitmate.backend.repository;

import com.bcl.fitmate.backend.common.enums.uploadFile.TargetType;
import com.bcl.fitmate.backend.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
    List<UploadFile> findAllByTargetIdAndTargetType(TargetType targetType, Long targetId);
}
