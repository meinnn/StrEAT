package io.ssafy.p.j11a307.order.service;

import io.ssafy.p.j11a307.order.dto.ReadStoreBasicInfoDTO;
import io.ssafy.p.j11a307.order.dto.ReadStoreDTO;
import io.ssafy.p.j11a307.order.global.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "STORE")
public interface StoreClient {
    @GetMapping("/api/stores/{id}")
    public DataResponse<ReadStoreDTO> getStoreInfo(@PathVariable Integer id);


    @GetMapping("/api/stores/{id}/photo-name")
    DataResponse<ReadStoreBasicInfoDTO> getStoreBasicInfo(@PathVariable Integer id);
}