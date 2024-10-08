package io.ssafy.p.j11a307.push_alert.service;

import io.ssafy.p.j11a307.push_alert.dto.internalapi.ApiResponse;
import io.ssafy.p.j11a307.push_alert.dto.internalapi.FcmTokenResponse;
import io.ssafy.p.j11a307.push_alert.global.DataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "user")
public interface UserService {

    @GetMapping("/api/users/{user-id}/fcm-token")
    ApiResponse<FcmTokenResponse> getFcmTokenByUserId(@PathVariable("user-id") Integer userId, @RequestHeader("X-Internal-Request") String internalRequestHeader);

    @GetMapping("/api/users/dibs/{storeId}/dibs-customers")
    DataResponse<List<Integer>> getCalledDibsUserByStoreId(@PathVariable("storeId") Integer storeId, @RequestHeader("X-Internal-Request") String internalRequestHeader);

    @GetMapping("/api/users/user-id")
    Integer getUserId(@RequestParam("accessToken") String accessToken, @RequestHeader("X-Internal-Request") String internalRequestHeader);
}
