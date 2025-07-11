package com.bcl.fitmate.backend.dto.note.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteResponseDto {
    private Long id;
    private String noteText;
    private Long noteWriter;
    private Long noteReceiver;
    private String noteCreateTime;
}
