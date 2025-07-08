package com.bcl.fitmate.backend.repository;

import com.bcl.fitmate.backend.entity.TrainerLicense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerLicenseRepository extends JpaRepository<TrainerLicense, Long> {
    Optional<List<TrainerLicense>> findByTrainerId(Long trainerId);

    TrainerLicense findTopByTrainerIdOrderByIdDesc(Long id);
}
