package com.bcl.fitmate.backend.entity;

import com.bcl.fitmate.backend.common.enums.trainer.TrainerStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "trainer_list_view")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Immutable
@Getter
public class TrainerListView {
    @Id
    @Column(name = "trainer_id")
    private Long trainerId;

    @Column(name = "username")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "job_address")
    private String jobAddress;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TrainerStatus status;
}
