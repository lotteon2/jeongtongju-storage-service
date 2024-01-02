package com.jeontongju.storage.client;

import com.jeontongju.storage.dto.request.RenderRequestDto;
import com.jeontongju.storage.dto.response.AssetResponseDto;
import com.jeontongju.storage.dto.response.RenderResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "convert-service", url = "${convert-service.url}")
public interface ConvertServiceClient {

  @PostMapping(value = "/v1/render", headers = {"Content-Type=application/json", "x-api-key=${convert-service.api-key}"})
  RenderResponseDto videoToGif(@RequestBody RenderRequestDto renderRequestDto);

  @GetMapping(value = "/serve/v1/assets/render/{id}", headers = "x-api-key=${convert-service.api-key}")
  AssetResponseDto getRenderAsset(@PathVariable String id);

}
