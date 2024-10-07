package io.ssafy.p.j11a307.push_alert.service;

import io.ssafy.p.j11a307.push_alert.dto.internalapi.ApiResponse;
import io.ssafy.p.j11a307.push_alert.dto.internalapi.FcmTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user")
public interface UserService {

    @GetMapping("/api/users/{user-id}/fcm-token")
    ApiResponse<FcmTokenResponse> getFcmTokenByUserId(@PathVariable("user-id") Integer userId, @RequestHeader("X-Internal-Request") String internalRequestHeader);
}
