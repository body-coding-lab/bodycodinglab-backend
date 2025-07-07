package com.bcl.fitmate.backend.repository;

import com.bcl.fitmate.backend.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    @Query("SELECT t FROM Trainer t JOIN t.user u WHERE u.name LIKE %:name%")
    List<Trainer> findTrainerByName(@Param("name") String name);

    @Query("SELECT t FROM Trainer t WHERE t.jobAddress LIKE %:jobAddress%")
    List<Trainer> findTrainerByAddress(@Param("jobAddress") String jobAddress);

    @Query("SELECT t FROM Trainer t JOIN FETCH t.user u LEFT JOIN FETCH u.profileImage")
    List<Trainer> findAllWithUserAndProfileImage();
}
