package com.bcl.fitmate.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "matches",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"member_id", "trainer_id"}),
                @UniqueConstraint(columnNames = {"member_Id"})
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Match {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    private User member;

    @ManyToOne
    @JoinColumn(name = "trainer_id", referencedColumnName = "id", nullable = false)
    private User trainer;

    @Column(name = "match_at", nullable = false)
    private LocalDateTime matchedAt;

    @Column(name = "is_maintained")
    private Boolean isMaintained = true;
}
