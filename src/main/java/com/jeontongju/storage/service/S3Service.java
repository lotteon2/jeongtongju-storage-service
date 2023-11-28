package com.jeontongju.storage.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.jeontongju.storage.dto.response.PresignedUrlResDto;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Service {

  private final AmazonS3Client amazonS3Client;

  @Value("${cloud.aws.s3.bucket}")
  public String bucket;  // S3 버킷

  @Value("${cloud.aws.region.static}")
  private String region;  // S3 리전


  public PresignedUrlResDto getPresignedUrl(String fileName) {
    String encodeFileName = UUID.randomUUID() + fileName;
    String objectKey = "dir/" + encodeFileName;
    String dataUrl = bucket + ".s3." + region + ".amazonaws.com/" + objectKey;

    Date expiration = new Date();
    long expTimeMillis = expiration.getTime() + (3 * 60 * 1000); // 3 minutes
    expiration.setTime(expTimeMillis); // Set URL expiration time

    GeneratePresignedUrlRequest generatePresignedUrlRequest =
        new GeneratePresignedUrlRequest(bucket, objectKey)
            .withMethod(HttpMethod.PUT)
            .withExpiration(expiration);

    return PresignedUrlResDto.builder()
        .presignedUrl(amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest).toString())
        .encodeFileName(encodeFileName)
        .dataUrl(dataUrl)
        .build();
  }

}