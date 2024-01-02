package com.jeontongju.storage.execption;

public class DurationOverException extends RuntimeException {
  public static final String message = "영상이 제한 시간을 초과했습니다.";

  public DurationOverException() {
    super(message);
  }

}
