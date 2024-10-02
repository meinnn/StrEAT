package io.ssafy.p.j11a307.order.service;

import io.ssafy.p.j11a307.order.dto.ReadStoreDTO;
import io.ssafy.p.j11a307.order.global.DataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "STORE")
public interface StoreClient {
    @GetMapping("/api/stores/{id}")
    public DataResponse<ReadStoreDTO> getStoreInfo(@PathVariable Integer id);
}