package io.ssafy.p.j11a307.store.service;

import io.ssafy.p.j11a307.store.dto.OwnerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service")
public interface OwnerClient {
    @GetMapping("/api/users/user-id")
    Integer getUserId(String accessToken, @RequestHeader("X-Internal-Request") String internalRequestHeader);

}
