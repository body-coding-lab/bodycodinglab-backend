package com.bcl.fitmate.backend.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "board_comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Comment extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commenter_id", nullable = false)
    private User commenter;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;
}
