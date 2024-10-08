package io.ssafy.p.j11a307.announcement.service;

import io.ssafy.p.j11a307.announcement.dto.ReadStoreDTO;
import io.ssafy.p.j11a307.announcement.global.DataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "STORE")
public interface StoreClient {
    @GetMapping("/api/stores/user/{userId}")
    Integer getStoreIdByUserId(@PathVariable Integer userId);

    @GetMapping("/api/stores/{id}")
    DataResponse<ReadStoreDTO> getStoreInfo(@PathVariable Integer id);

}
