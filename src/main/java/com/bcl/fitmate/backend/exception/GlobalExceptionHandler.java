package com.bcl.fitmate.backend.exception;

import com.bcl.fitmate.backend.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ IllegalArgumentException.class, IllegalStateException.class })
    public ResponseEntity<ResponseDto<?>> handleBadRequest(RuntimeException e) {
        return logAndRespond("", "", HttpStatus.OK, e);
    }

    private ResponseEntity<ResponseDto<?>> logAndRespond(String code, String message, HttpStatus status, Exception e) {
        e.printStackTrace();
        return ResponseDto.failWithStatus(code, message, status);
    }
}
