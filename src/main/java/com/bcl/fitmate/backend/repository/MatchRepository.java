package com.bcl.fitmate.backend.repository;

import com.bcl.fitmate.backend.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    Optional<Match> findByMember_Id(Long memberId);

    Optional<Match> findByTrainer_Id(Long trainerId);

//    boolean existByIdAndMemberIdOrTrainerId(Long matchId, Long memberId, Long trainerId);

    long count();
}
