package com.bcl.fitmate.backend.dto.note.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetNoteResponseDto {
    private Long id;
    private String noteText;
    private String noteWriter;
    private String noteReceiver;
    private String noteCreateTime;
    private boolean isRead;



}
