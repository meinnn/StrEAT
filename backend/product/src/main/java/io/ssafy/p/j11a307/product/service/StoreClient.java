package io.ssafy.p.j11a307.product.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "STORE")
public interface StoreClient {
    @GetMapping("/api/stores/user/{userId}")
    Integer getStoreIdByUserId(@PathVariable Integer userId);
}