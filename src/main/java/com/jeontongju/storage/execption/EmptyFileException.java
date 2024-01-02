package com.jeontongju.storage.execption;

public class EmptyFileException extends RuntimeException {
  private static final String message = "파일이 비어있습니다.";

  public EmptyFileException() {
    super(message);
  }

}
