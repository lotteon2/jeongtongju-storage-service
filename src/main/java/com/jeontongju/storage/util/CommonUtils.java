package com.jeontongju.storage.util;

import java.util.UUID;

public class CommonUtils {

  private static final String FILE_EXTENSION_SEPARATOR = ".";
  private static final String CATEGORY_PREFIX = "/";

  public static String buildFileName(String category, String originalFileName) {
    int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
    String fileExtension = originalFileName.substring(fileExtensionIndex);
    String fileName = UUID.randomUUID() + originalFileName.substring(0, fileExtensionIndex);

    return category + CATEGORY_PREFIX + fileName  + fileExtension;
  }

  public static String buildDataUrl(String bucket, String region, String encodeFileName) {
    return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + encodeFileName;
  }

}
