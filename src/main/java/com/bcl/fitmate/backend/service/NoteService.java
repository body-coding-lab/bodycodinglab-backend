package com.bcl.fitmate.backend.service;


import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.note.reqeust.NoteRequestDto;
import com.bcl.fitmate.backend.dto.note.response.CreateNoteResponseDto;
import com.bcl.fitmate.backend.dto.note.response.GetNoteListResponseDto;
import com.bcl.fitmate.backend.dto.note.response.GetNoteResponseDto;
import com.bcl.fitmate.backend.entity.Note;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface NoteService {

    Note getNoteById(Long noteId);

    ResponseDto<CreateNoteResponseDto> createNote(Long userId, @Valid NoteRequestDto dto);

    ResponseDto<Page<GetNoteListResponseDto>> getAllNote(Long userId, Pageable pageable);

    ResponseDto<GetNoteResponseDto> getNoteById(Long userId, Long noteId);

    ResponseDto<Void> deleteNote(Long userId, Long noteId);

    ResponseDto<Page<GetNoteListResponseDto>> getReceivedNotes(Long userId, Pageable pageable);

    ResponseDto<Page<GetNoteListResponseDto>> getSentNotes(Long userId, Pageable pageable);
}
