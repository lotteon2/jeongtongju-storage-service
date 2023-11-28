package com.jeontongju.storage.controller;

import com.jeontongju.storage.dto.response.PresignedUrlResDto;
import com.jeontongju.storage.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StorageController {
  private final S3Service s3Service;

  @GetMapping("/file/{fileName}")
  public ResponseEntity<PresignedUrlResDto> getPresignedUrl(@PathVariable String fileName) {
    return ResponseEntity.ok(s3Service.getPresignedUrl(fileName));
  }
}
