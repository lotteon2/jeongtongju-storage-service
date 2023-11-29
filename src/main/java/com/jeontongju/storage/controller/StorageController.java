package com.jeontongju.storage.controller;

import com.jeontongju.storage.dto.response.PresignedUrlResDto;
import com.jeontongju.storage.dto.temp.ResponseFormat;
import com.jeontongju.storage.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StorageController {
  private final S3Service s3Service;

  @PostMapping("/file/{fileName}")
  public ResponseEntity<ResponseFormat<PresignedUrlResDto>> getPresignedUrl(@PathVariable String fileName) {

    return ResponseEntity.ok()
        .body(
            ResponseFormat.<PresignedUrlResDto>builder()
                .code(HttpStatus.OK.value())
                .detail("업로드 경로 조회 성공")
                .message(HttpStatus.OK.name())
                .data(s3Service.getPresignedUrl(fileName))
                .build()
            );
  }
}
