package com.jeontongju.storage.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RenderRequestDto {

  private Timeline timeline;
  private Output output;

  public static RenderRequestDto of(
      String src, Integer length, Integer start,
      Integer width, Integer height
  ) {
    return RenderRequestDto.builder()
        .timeline(Timeline.of(src, length, start))
        .output(Output.of(width, height))
        .build();
  }

  @Getter
  @Builder
  public static class Asset {

    private String type;
    private String src;
    private Integer trim;

    public static Asset of(String src) {
      return Asset.builder()
          .type("video")
          .src(src)
          .trim(3)
          .build();
    }
  }

  @Getter
  @Builder
  public static class Offset {

    @Builder.Default
    private Integer x = 0;

    @Builder.Default
    private Integer y = 0;
  }

  @Getter
  @Builder
  public static class Clip {

    private Asset asset;
    private Offset offset;
    private String position;
    private Integer length;
    private Integer start;

    public static Clip of(String src, Integer length, Integer start) {
      return Clip.builder()
          .asset(Asset.of(src))
          .offset(Offset.builder().build())
          .position("center")
          .length(length)
          .start(start)
          .build();
    }
  }

  @Getter
  @Builder
  public static class Track {

    private List<Clip> clips;

    public static Track of(String src, Integer length, Integer start) {
      return Track.builder()
          .clips(List.of(Clip.of(src, length, start)))
          .build();
    }
  }

  @Getter
  @Builder
  public static class Timeline {

    private String background;
    private List<Track> tracks;

    public static Timeline of(String src, Integer length, Integer start) {
      return Timeline.builder()
          .background("#000000")
          .tracks(List.of(Track.of(src, length, start)))
          .build();
    }
  }

  @Getter
  @Builder
  public static class Size {

    private Integer width;
    private Integer height;

    public static Size of(Integer width, Integer height) {
      return Size.builder()
          .width(width)
          .height(height)
          .build();
    }
  }

  @Getter
  @Builder
  public static class Output {

    private Size size;
    private String format;
    private Boolean repeat;
    private String quality;
    private Integer fps;

    public static Output of(Integer width, Integer height) {
      return Output.builder()
          .size(Size.of(width, height))
          .format("gif")
          .repeat(true)
          .quality("low")
          .fps(15)
          .build();
    }
  }

}
