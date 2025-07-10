package com.bcl.fitmate.backend.repository;

import com.bcl.fitmate.backend.common.enums.user.UserRole;
import com.bcl.fitmate.backend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(UserRole name);
}
