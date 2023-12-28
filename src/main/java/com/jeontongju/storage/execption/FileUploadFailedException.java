package com.jeontongju.storage.execption;

public class FileUploadFailedException extends RuntimeException {
  private static final String message = "파일 업로드에 실패했습니다.";

  public FileUploadFailedException() {
    super(message);
  }
}
