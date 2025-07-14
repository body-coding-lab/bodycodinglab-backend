package com.bcl.fitmate.backend.controller;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.config.security.UserPrincipal;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.note.reqeust.NoteRequestDto;
import com.bcl.fitmate.backend.dto.note.response.CreateNoteResponseDto;
import com.bcl.fitmate.backend.dto.note.response.GetNoteListResponseDto;
import com.bcl.fitmate.backend.dto.note.response.GetNoteResponseDto;
import com.bcl.fitmate.backend.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.NOTE_API)
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    private static final String FIND = "/{noteId}";
    private static final String RECEIVED = "/received";
    private static final String SENT = "/sent";

    @PreAuthorize("hasAnyRole('MEMBER', 'TRAINER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseDto<CreateNoteResponseDto>> createNote(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid@RequestBody NoteRequestDto dto
            ){
        Long userId = userPrincipal.getId();

        return ResponseDto.toResponseEntity(HttpStatus.CREATED, noteService.createNote(userId, dto));
    }

    @PreAuthorize("hasAnyRole('MEMBER', 'TRAINER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<ResponseDto<Page<GetNoteListResponseDto>>> getAllNote(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Long userId = userPrincipal.getId();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return ResponseDto.toResponseEntity(HttpStatus.OK, noteService.getAllNote(userId, pageable));
    }

    @PreAuthorize("hasAnyRole('MEMBER', 'TRAINER', 'ADMIN')")
    @GetMapping(FIND)
    public ResponseEntity<ResponseDto<GetNoteResponseDto>> getNoteById(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long noteId
    ){
        Long userId = userPrincipal.getId();

        return ResponseDto.toResponseEntity(HttpStatus.OK, noteService.getNoteById(userId, noteId));
    }

    @PreAuthorize("hasAnyRole('MEMBER', 'TRAINER', 'ADMIN')")
    @DeleteMapping(FIND)
    public ResponseEntity<ResponseDto<Void>> deleteNote(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long noteId
    ){
        Long userId = userPrincipal.getId();

        return ResponseDto.toResponseEntity(HttpStatus.OK, noteService.deleteNote(userId, noteId));
    }

    @PreAuthorize("hasAnyRole('MEMBER', 'TRAINER', 'ADMIN')")
    @GetMapping(RECEIVED)
    public ResponseEntity<ResponseDto<Page<GetNoteListResponseDto>>> getReceivedNotes(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Long userId = userPrincipal.getId();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return ResponseDto.toResponseEntity(HttpStatus.OK, noteService.getReceivedNotes(userId, pageable));
    }


    @PreAuthorize("hasAnyRole('MEMBER', 'TRAINER', 'ADMIN')")
    @GetMapping(SENT)
    public ResponseEntity<ResponseDto<Page<GetNoteListResponseDto>>> getSentNotes(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Long userId = userPrincipal.getId();
        Pageable pageable =  PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return ResponseDto.toResponseEntity(HttpStatus.OK, noteService.getSentNotes(userId, pageable));
    }
}
