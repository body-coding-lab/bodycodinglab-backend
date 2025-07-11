package com.bcl.fitmate.backend.entity;

import com.bcl.fitmate.backend.common.enums.matchWaitingList.ApprovedStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "match_waiting_list",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"member_Id"})
        }
        )
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class MatchWaitingList {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",  referencedColumnName = "id", nullable = false, unique = true )
    private User member;

    @ManyToOne
    @JoinColumn(name = "trainer_id", referencedColumnName = "id", nullable = false)
    private User trainer;

    @Column(name = "applied_at", nullable = false)
    private LocalDateTime appliedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "approved_status", nullable = false)
    private ApprovedStatus approvedStatus;

    @Column(name = "reject_response", nullable = true)
    private String rejectResponse;
}
