package io.ssafy.p.j11a307.user.service;

import io.ssafy.p.j11a307.user.dto.DibsStoreStatusResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "store")
public interface StoreService {

    @GetMapping("/api/stores/dibs")
    List<DibsStoreStatusResponse> getStoreStatusByIds(@RequestParam List<Integer> storeIds);

    @GetMapping("/api/stores/user/{userId}")
    Integer getStoreIdByUserId(@PathVariable("userId") Integer userId);
}
