package com.example.backend.config;

import com.example.backend.dtos.ResponseDto;
import com.example.backend.exceptions.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = { AppException.class })
    @ResponseBody
    public ResponseEntity<ResponseDto> handleException(AppException ex) {
        ResponseDto responseDto = ResponseDto.builder()
                .isSuccess(false)
                .message(ex.getMessage())
                .data(null)
                .build();

        return ResponseEntity
                .status(ex.getStatus())
                .body(responseDto);
    }

    @ExceptionHandler(value = { Exception.class })
    @ResponseBody
    public ResponseEntity<ResponseDto> handleException(Exception ex) {
        ResponseDto responseDto = ResponseDto.builder()
                .isSuccess(false)
                .message(ex.getMessage())
                .data(null)
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(responseDto);
    }
}
