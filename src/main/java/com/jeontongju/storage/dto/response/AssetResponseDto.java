package com.jeontongju.storage.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AssetResponseDto {

  private List<Data> data;

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Data {
    private String type;
    private Attributes attributes;
  }

  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Attributes {
    private String id;
    private String owner;
    private String provider;
    private String region;
    private String renderId;
    private String providerId;
    private String filename;
    private String url;
    private String status;
    private String created;
    private String updated;
  }
}
