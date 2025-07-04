package com.bcl.fitmate.backend.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "trainer_status_logs")
@EntityListeners(AuditingEntityListener.class)
public class TrainerStatusLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "trainer_id", nullable = false)
    private Long trainerId;

    @Column(name = "username")
    private String username;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "prev_status")
//    private TrainerStatus prevStatus;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "new_status")
//    private TrainerStatus newStatus;

    @Column(name = "changed_by")
    private Long changedBy;

    @Column(name = "changed_by_username")
    private String changedByUsername;

    @Column(name = "change_reason")
    private String changeReason;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;
}
