package io.ssafy.p.j11a307.store.service;

import io.ssafy.p.j11a307.store.dto.OwnerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "owner-service")
public interface OwnerClient {
    @GetMapping("api/owners/{id}")
    OwnerResponse getOwnerById(@PathVariable("id") Long id);

}
