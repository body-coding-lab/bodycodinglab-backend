package com.bcl.fitmate.backend.entity;

import com.bcl.fitmate.backend.common.enums.board.Category;
import com.bcl.fitmate.backend.common.enums.trainer.LicenseType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "boards")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Board extends BaseTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "match_id")
    private Match match;

    @Column(nullable = false, name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    @JoinColumn(nullable = false, name = "writer_id")
    private User writer;

    @Column(nullable = false, name = "title")
    private String title;

    @Column(nullable = false, name = "content")
    private String content;

    @Column(nullable = false, name = "view_count")
    private Long viewCount = 0L;

    @Column(nullable = false, name = "like")
    private Long like = 0L;
}
