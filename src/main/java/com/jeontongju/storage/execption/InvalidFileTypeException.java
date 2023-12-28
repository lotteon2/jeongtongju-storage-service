package com.jeontongju.storage.execption;

public class InvalidFileTypeException extends RuntimeException {
  private static final String message = "잘못된 타입입니다.";

  public InvalidFileTypeException() {
    super(message);
  }
}
