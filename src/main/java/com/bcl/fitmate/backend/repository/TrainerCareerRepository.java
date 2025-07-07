package com.bcl.fitmate.backend.repository;

import com.bcl.fitmate.backend.entity.TrainerCareer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerCareerRepository extends JpaRepository<TrainerCareer, Long> {
    Optional<List<TrainerCareer>> findByTrainerId(Long trainerId);

    TrainerCareer findTopByTrainerIdOrderByCompanyQuitDesc(Long id);
}
