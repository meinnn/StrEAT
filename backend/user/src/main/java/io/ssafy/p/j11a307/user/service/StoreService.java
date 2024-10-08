package io.ssafy.p.j11a307.user.service;

import io.ssafy.p.j11a307.user.dto.DibsStoreStatusResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "store")
public interface StoreService {

    @GetMapping("/api/stores/dibs")
    List<DibsStoreStatusResponse> getStoreStatusByIds(List<Integer> storeIds);
}
