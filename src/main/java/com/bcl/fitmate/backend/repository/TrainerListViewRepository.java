package com.bcl.fitmate.backend.repository;

import com.bcl.fitmate.backend.common.enums.trainer.TrainerStatus;
import com.bcl.fitmate.backend.entity.TrainerListView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface TrainerListViewRepository extends CrudRepository<TrainerListView, Long> {
    Page<TrainerListView> findAll(Pageable pageable);
    Page<TrainerListView> findByStatus(TrainerStatus status, Pageable pageable);
}
