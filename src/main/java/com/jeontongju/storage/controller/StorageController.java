package com.jeontongju.storage.controller;

import com.jeontongju.storage.dto.response.PresignedUrlResDto;
import com.jeontongju.storage.dto.response.ShortsResponseDto;
import com.jeontongju.storage.service.S3Service;
import io.github.bitbox.bitbox.dto.ResponseFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StorageController {

  private final S3Service s3Service;

  @PostMapping("/file/{fileName}")
  public ResponseEntity<ResponseFormat<PresignedUrlResDto>> getPresignedUrl(
      @PathVariable String fileName) {

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

  @PostMapping("/upload/shorts")
  public ResponseEntity<ResponseFormat<ShortsResponseDto>> uploadShorts(
      @RequestPart MultipartFile shorts
  ) {
    String uploadUrl = s3Service.uploadFileV1("shorts", shorts);

    return ResponseEntity.ok()
        .body(
            ResponseFormat.<ShortsResponseDto>builder()
                .code(HttpStatus.OK.value())
                .detail("업로드 성공")
                .message(HttpStatus.OK.name())
                .data(s3Service.uploadShorts(uploadUrl))
                .build()
        );
  }
}
