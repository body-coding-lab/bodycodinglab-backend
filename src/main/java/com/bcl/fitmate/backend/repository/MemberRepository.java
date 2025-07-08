package com.bcl.fitmate.backend.repository;

import com.bcl.fitmate.backend.entity.Board;
import com.bcl.fitmate.backend.entity.Member;
import com.bcl.fitmate.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserId(Long userId);

    Optional<Member> findByUser(User user);
}
