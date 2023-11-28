package com.jeontongju.storage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PresignedUrlResDto {

  private String presignedUrl;
  private String encodeFileName;
  private String dataUrl;
}
