package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.common.util.DateUtils;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.note.reqeust.NoteRequestDto;
import com.bcl.fitmate.backend.dto.note.response.CreateNoteResponseDto;
import com.bcl.fitmate.backend.dto.note.response.GetNoteListResponseDto;
import com.bcl.fitmate.backend.dto.note.response.GetNoteResponseDto;
import com.bcl.fitmate.backend.entity.Note;
import com.bcl.fitmate.backend.entity.User;
import com.bcl.fitmate.backend.repository.NoteRepository;
import com.bcl.fitmate.backend.repository.UserRepository;
import com.bcl.fitmate.backend.service.NoteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));
    }

    @Override
    public Note getNoteById(Long noteId) {
        return noteRepository.findById(noteId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_NOTE));
    }


    @Override
    @Transactional
    public ResponseDto<CreateNoteResponseDto> createNote(Long userId, NoteRequestDto dto) {
        CreateNoteResponseDto response = null;

        User writer = getUserById(userId);

        User receiver = getUserById(userId);

        Note note = Note.builder()
                .noteText(dto.getNoteText())
                .noteWriter(writer)
                .noteReceiver(receiver)
                .noteCreateTime(LocalDateTime.now())
                .build();

        writer.addWriterNotes(note);
        receiver.addReceiverNotes(note);
        noteRepository.save(note);

        response = new CreateNoteResponseDto(
                note.getId()
        );

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }

    @Override
    public ResponseDto<Page<GetNoteListResponseDto>> getAllNote(Long userId, Pageable pageable) {
        Page<GetNoteListResponseDto> response = null;

        Page<Note> notePage = noteRepository.findByNoteWriter_IdOrNoteReceiver_Id(userId, userId, pageable);

        response = notePage.map(note -> new GetNoteListResponseDto(
                note.getId(),
                note.getNoteText(),
                note.getNoteWriter().getName(),
                note.getNoteReceiver().getName(),
                DateUtils.format(note.getNoteCreateTime())
        ));

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }

    @Override
    public ResponseDto<GetNoteResponseDto> getNoteById(Long userId, Long noteId) {
        GetNoteResponseDto response = null;

        Note note = getNoteById(noteId);

        if(!note.getNoteWriter().getId().equals(userId) && !note.getNoteReceiver().getId().equals(userId)){
            throw new EntityNotFoundException(ResponseMessage.NOT_EXISTS_NOTE_PERMISSION);
        }

        if(note.getNoteReceiver().getId().equals(userId) && !note.isRead()){
            note.setRead(true);
            noteRepository.save(note);
        }

        response = new GetNoteResponseDto(
                note.getId(),
                note.getNoteText(),
                note.getNoteWriter().getName(),
                note.getNoteReceiver().getName(),
                DateUtils.format(note.getNoteCreateTime()),
                note.isRead()
        );

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }

    @Override
    @Transactional
    public ResponseDto<Void> deleteNote(Long userId, Long noteId) {
        Note note = getNoteById(noteId);

        if(!note.getNoteWriter().getId().equals(userId) && !note.getNoteReceiver().getId().equals(userId)){
             throw new EntityNotFoundException(ResponseMessage.NOT_EXISTS_NOTE_PERMISSION);
        }

        User writer = note.getNoteWriter();
        User receiver = note.getNoteReceiver();

        writer.removeWriterNotes(note);
        receiver.removeReceiverNotes(note);

        noteRepository.delete(note);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    public ResponseDto<Page<GetNoteListResponseDto>> getReceivedNotes(Long userId, Pageable pageable) {
        Page<GetNoteListResponseDto> response = null;

        Page<Note> notes = noteRepository.findByNoteReceiver_Id(userId, pageable);

        response = notes.map(note -> new GetNoteListResponseDto(
                note.getId(),
                note.getNoteText(),
                note.getNoteWriter().getName(),
                note.getNoteReceiver().getName(),
                DateUtils.format(note.getNoteCreateTime())
        ));

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }

    @Override
    public ResponseDto<Page<GetNoteListResponseDto>> getSentNotes(Long userId, Pageable pageable) {
        Page<GetNoteListResponseDto> response = null;

        Page<Note> notes = noteRepository.findByNoteWriter_Id(userId, pageable);

        response = notes.map(note -> new GetNoteListResponseDto(
                note.getId(),
                note.getNoteText(),
                note.getNoteWriter().getName(),
                note.getNoteReceiver().getName(),
                DateUtils.format(note.getNoteCreateTime())
        ));

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }
}
