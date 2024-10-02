package io.ssafy.p.j11a307.product.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "store")
public interface StoreClient {

    @GetMapping("/api/stores/user")
    Integer getStoreIdByUserId(@RequestParam("userId") Integer userId);
}