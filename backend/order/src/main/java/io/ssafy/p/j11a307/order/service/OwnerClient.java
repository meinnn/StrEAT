package io.ssafy.p.j11a307.order.service;

import io.ssafy.p.j11a307.order.dto.UserInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "USER")
public interface OwnerClient {
    @GetMapping("/api/users/user-id")
    Integer getUserId(@RequestParam("accessToken") String accessToken, @RequestHeader("X-Internal-Request") String internalRequestHeader);

    //User 정보 불러오는 API 필요
    @GetMapping("/api/users/{userId}")
    public UserInfoResponse getUserInformation(@PathVariable Integer userId);

}