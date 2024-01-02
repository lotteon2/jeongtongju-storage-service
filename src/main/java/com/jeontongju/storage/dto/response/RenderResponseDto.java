package com.jeontongju.storage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RenderResponseDto {

  private Boolean success;
  private String message;
  private RenderDetailResponseDto response;

  @Getter
  @AllArgsConstructor
  public static class RenderDetailResponseDto {
    private String message;
    private String id;
  }

}
