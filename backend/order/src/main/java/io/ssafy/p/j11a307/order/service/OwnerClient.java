package io.ssafy.p.j11a307.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service")
public interface OwnerClient {
    @GetMapping("/api/users/user-id")
    Integer getUserId(String accessToken, @RequestHeader("X-Internal-Request") String internalRequestHeader);

}
