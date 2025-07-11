package com.bcl.fitmate.backend.repository;

import com.bcl.fitmate.backend.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    Page<Note> findByNoteReceiver_Id(Long receiverId, Pageable pageable);

    Page<Note> findByNoteWriter_Id(Long writerId, Pageable pageable);

    Page<Note> findByNoteWriter_IdOrNoteReceiver_Id(Long noteWriter, Long noteReceiver, Pageable pageable);
}
