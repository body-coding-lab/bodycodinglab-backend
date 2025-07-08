package com.bcl.fitmate.backend.repository;

import com.bcl.fitmate.backend.entity.MemberForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberFormRepository extends JpaRepository<MemberForm, Long> {
}
