package com.bcl.fitmate.backend.dto.note.reqeust;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class NoteRequestDto {
    @NotBlank(message = "텍스트는 필수 입력 값입니다.")
    private String noteText;
    @NotNull(message = "받는 사람은 필수 입력 값입니다.")
    private Long noteReceiver;
}
