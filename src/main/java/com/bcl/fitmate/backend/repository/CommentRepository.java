package com.bcl.fitmate.backend.repository;

import com.bcl.fitmate.backend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
