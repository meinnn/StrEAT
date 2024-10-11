package io.ssafy.p.j11a307.order.service;

import io.ssafy.p.j11a307.order.dto.ResponseData;
import io.ssafy.p.j11a307.order.dto.ResponseData2;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoMapClient", url = "https://dapi.kakao.com/v2/local")

public interface KakaoClient {
    @GetMapping("/search/keyword.json")
    ResponseEntity<ResponseData2> searchLocation(@RequestParam("query") String query,
                                                 @RequestHeader("Authorization") String authorization);



}
