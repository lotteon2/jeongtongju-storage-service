package com.jeontongju.storage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortsResponseDto {

  private String dataUrl;
  private String previewUrl;

  public static ShortsResponseDto of(String dataUrl, String previewUrl) {
    return ShortsResponseDto.builder()
        .dataUrl(dataUrl)
        .previewUrl(previewUrl)
        .build();
  }
}
