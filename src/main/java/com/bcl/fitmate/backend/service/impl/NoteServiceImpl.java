package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.note.reqeust.NoteRequestDto;
import com.bcl.fitmate.backend.dto.note.response.NoteResponseDto;
import com.bcl.fitmate.backend.entity.Note;
import com.bcl.fitmate.backend.entity.User;
import com.bcl.fitmate.backend.repository.NoteRepository;
import com.bcl.fitmate.backend.repository.UserRepository;
import com.bcl.fitmate.backend.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {


}
