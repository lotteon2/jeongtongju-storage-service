package com.jeontongju.storage.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.jeontongju.storage.client.ConvertServiceClient;
import com.jeontongju.storage.dto.request.RenderRequestDto;
import com.jeontongju.storage.dto.response.AssetResponseDto;
import com.jeontongju.storage.dto.response.PresignedUrlResDto;
import com.jeontongju.storage.dto.response.RenderResponseDto;
import com.jeontongju.storage.dto.response.ShortsResponseDto;
import com.jeontongju.storage.execption.DurationOverException;
import com.jeontongju.storage.execption.EmptyFileException;
import com.jeontongju.storage.execption.FileUploadFailedException;
import com.jeontongju.storage.execption.InvalidFileTypeException;
import com.jeontongju.storage.util.CommonUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.EncoderException;
import ws.schild.jave.InputFormatException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Service {

  private final AmazonS3Client amazonS3Client;

  private final ConvertServiceClient convertServiceClient;

  @Value("${cloud.aws.s3.bucket}")
  public String bucket;  // S3 버킷

  @Value("${cloud.aws.region.static}")
  private String region;  // S3 리전

  @Value("${convert-service.gif-url}")
  private String gifUrl;

  public PresignedUrlResDto getPresignedUrl(String fileName) throws UnsupportedEncodingException {
    fileName = URLEncoder.encode(fileName, "UTF-8");
    String encodeFileName = CommonUtils.buildFileName("dir", fileName);
    String dataUrl = CommonUtils.buildDataUrl(bucket, region, encodeFileName);

    Date expiration = new Date();
    long expTimeMillis = expiration.getTime() + (3 * 60 * 1000); // 3 minutes
    expiration.setTime(expTimeMillis); // Set URL expiration time

    GeneratePresignedUrlRequest generatePresignedUrlRequest =
        new GeneratePresignedUrlRequest(bucket, encodeFileName)
            .withMethod(HttpMethod.PUT)
            .withExpiration(expiration);

    return PresignedUrlResDto.builder()
        .presignedUrl(amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest).toString())
        .encodeFileName(encodeFileName)
        .dataUrl(dataUrl)
        .build();
  }

  public String uploadFileV1(String category, MultipartFile multipartFile) {
    validateFileExists(multipartFile);

    String fileName = CommonUtils.buildFileName(category, multipartFile.getOriginalFilename());

    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentType(multipartFile.getContentType());

    try (InputStream inputStream = multipartFile.getInputStream()) {
      amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata));
    } catch (IOException e) {
      throw new FileUploadFailedException();
    }

    return amazonS3Client.getUrl(bucket, fileName).toString();
  }

  public ShortsResponseDto uploadShorts(String uploadUrl) {
    RenderRequestDto request = RenderRequestDto.of(uploadUrl, 5, 0, 250, 250);

    RenderResponseDto renderResponse = convertServiceClient.videoToGif(request);
    String resultUrl =
        gifUrl + renderResponse.getResponse().getId() + "." + request.getOutput().getFormat();

    return ShortsResponseDto.of(uploadUrl, resultUrl);
  }

  private void validateFileExists(MultipartFile multipartFile) {
    if (multipartFile.isEmpty()) {
      throw new EmptyFileException();
    }
    if (!multipartFile.getContentType().contains("video")) {
      throw new InvalidFileTypeException();
    }

    File file = null;

    try {
      file = CommonUtils.convert(multipartFile).orElseThrow(InvalidFileTypeException::new);

      MultimediaObject multimediaObject = new MultimediaObject(file);
      MultimediaInfo multimediaInfo = multimediaObject.getInfo();
      long min = (multimediaInfo.getDuration() / 1000) / 60;
      long sec = (multimediaInfo.getDuration() / 1000) % 60;

      log.info("min : {}, sec : {}", min, sec);
      if (min >= 1L && sec != 0L) {
        throw new DurationOverException();
      }
    } catch (IOException | EncoderException e) {
      throw new FileUploadFailedException();
    } finally {
      if (file != null)
        file.delete();
    }
  }

}