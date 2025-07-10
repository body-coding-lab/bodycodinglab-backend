package com.bcl.fitmate.backend.repository;

import com.bcl.fitmate.backend.common.enums.board.Category;
import com.bcl.fitmate.backend.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findAllByMatchIdAndCategory(Long matchId, Category category, Pageable pageable);

    @Query("SELECT b FROM Board b " +
            "WHERE b.match.id = :matchId " +
            "AND b.category = :category " +
            "AND (:writerName IS NULL OR b.writer.name LIKE %:writerName%)")
    Page<Board> searchByWriterName(
            @Param("matchId") Long matchId,
            @Param("category") Category category,
            @Param("writerName") String writerName,
            Pageable pageable
    );

    @Query("SELECT b FROM Board b " +
            "WHERE b.match.id = :matchId " +
            "AND b.category = :category " +
            "AND (:title IS NULL OR b.title LIKE %:title%)")
    Page<Board> searchByTitle(
            @Param("matchId") Long matchId,
            @Param("category") Category category,
            @Param("title") String writerName,
            Pageable pageable
    );

    @Query("SELECT b FROM Board b " +
            "WHERE b.match.id = :matchId " +
            "AND b.category = :category " +
            "AND (:content IS NULL OR b.content LIKE %:content%)")
    Page<Board> searchByContent(
            @Param("matchId") Long matchId,
            @Param("category") Category category,
            @Param("content") String writerName,
            Pageable pageable
    );
}
