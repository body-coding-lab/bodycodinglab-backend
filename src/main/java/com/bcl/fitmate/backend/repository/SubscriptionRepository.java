package com.bcl.fitmate.backend.repository;


import com.bcl.fitmate.backend.entity.Board;
import com.bcl.fitmate.backend.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByMember_MemberId(Long memberId);
}
