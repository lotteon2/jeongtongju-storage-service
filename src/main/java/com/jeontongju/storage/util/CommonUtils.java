package com.jeontongju.storage.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public class CommonUtils {

  private static final String FILE_EXTENSION_SEPARATOR = ".";
  private static final String CATEGORY_PREFIX = "/";

  public static String buildFileName(String category, String originalFileName) {
    int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
    String fileExtension = originalFileName.substring(fileExtensionIndex);

    return category + CATEGORY_PREFIX + UUID.randomUUID() + fileExtension;
  }

  public static String buildDataUrl(String bucket, String region, String encodeFileName) {
    return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + encodeFileName;
  }

  public static Optional<File> convert(MultipartFile file) throws IOException {
    File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
    if (convertFile.createNewFile()) {
      try (FileOutputStream fos = new FileOutputStream(convertFile)) {
        fos.write(file.getBytes());
      }
      return Optional.of(convertFile);
    }
    return Optional.empty();
  }
}
