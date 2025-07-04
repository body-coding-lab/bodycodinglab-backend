package com.bcl.fitmate.backend.entity;

import com.bcl.fitmate.backend.common.enums.oneDayTicket.OneDayTicketStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "one_day_tickets")
public class OneDayTicket extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private User member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id", nullable = false)
    private User trainer;

    @Column(name = "issued_at", nullable = false)
    private LocalDate issuedAt;

    @Column(name = "used_at")
    private LocalDate usedAt;

    @Column(name = "canceled_at")
    private LocalDate canceledAt;

    @Column(name = "cancel_reason")
    private String cancelReason;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OneDayTicketStatus status;
}
