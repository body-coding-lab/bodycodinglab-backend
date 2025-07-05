package com.bcl.fitmate.backend.repository;

import com.bcl.fitmate.backend.entity.TrainerStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerStatusLogRepository extends JpaRepository<TrainerStatusLog, Long> {
}
