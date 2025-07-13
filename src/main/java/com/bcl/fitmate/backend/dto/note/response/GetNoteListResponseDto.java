package com.bcl.fitmate.backend.dto.note.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetNoteListResponseDto {
    private Long id;
    private String noteText;
    private String noteWriter;
    private String noteReceiver;
    private String noteCreateTime;
}
