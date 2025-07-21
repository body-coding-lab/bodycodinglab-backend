package com.bcl.fitmate.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "note_text", nullable = false)
    private String noteText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_writer",nullable = false)
    private User noteWriter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_receiver", nullable = false)
    private User noteReceiver;

    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;

    @Column(name = "sender_deleted", nullable = false)
    private boolean senderDeleted = false;

    @Column(name = "receiver_deleted", nullable = false)
    private boolean receiverDeleted = false;
}

