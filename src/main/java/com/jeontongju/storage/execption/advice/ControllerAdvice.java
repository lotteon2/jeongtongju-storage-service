package com.jeontongju.storage.execption.advice;

import com.jeontongju.storage.execption.EmptyFileException;
import com.jeontongju.storage.execption.FileUploadFailedException;
import io.github.bitbox.bitbox.dto.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvice extends ResponseEntityExceptionHandler {

  @ExceptionHandler(EmptyFileException.class)
  public ResponseEntity<ResponseFormat<Void>> handleEmptyFileException(
      EmptyFileException e) {
    HttpStatus status = HttpStatus.BAD_REQUEST;

    return ResponseEntity
        .status(status.value())
        .body(
            ResponseFormat.<Void>builder()
                .code(status.value())
                .message(status.name())
                .detail(e.getMessage())
                .build()
        );
  }

  @ExceptionHandler(FileUploadFailedException.class)
  public ResponseEntity<ResponseFormat<Void>> handleFileUploadFailedException(
      FileUploadFailedException e) {
    HttpStatus status = HttpStatus.BAD_REQUEST;

    return ResponseEntity
        .status(status.value())
        .body(
            ResponseFormat.<Void>builder()
                .code(status.value())
                .message(status.name())
                .detail(e.getMessage())
                .build()
        );
  }

}
