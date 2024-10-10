package io.ssafy.p.j11a307.order.service;

import io.ssafy.p.j11a307.order.dto.ResponseData;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="test", url="http://apis.data.go.kr/B553077/api/open/sdsc2")
public interface OpenAPIClient {
    @GetMapping("/storeListInRadius")
    ResponseEntity<ResponseData> storeListInRadius(@RequestParam("serviceKey") String serviceKey,
                                                   @RequestParam("type") String type,
                                                   @RequestParam("numOfRows") String numOfRows,
                                                   @RequestParam("pageNo") String pageNo,
                                                   @RequestParam("indsLclsCd") String indsLclsCd,
                                                   @RequestParam("indsMclsCd") String indsMclsCd,
                                                   @RequestParam("indsSclsCd") String indsSclsCd,
                                                   @RequestParam("radius") String radius,
                                                   @RequestParam("cx") String cx,
                                                   @RequestParam("cy") String cy);


}
