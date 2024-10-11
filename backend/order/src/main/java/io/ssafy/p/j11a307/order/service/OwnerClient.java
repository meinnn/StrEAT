package io.ssafy.p.j11a307.order.service;

import io.ssafy.p.j11a307.order.dto.UserInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @GetMapping("/api/users/customer-id")
    Integer getCustomerId(@RequestParam("accessToken") String accessToken, @RequestHeader("X-Internal-Request") String internalRequestHeader);

    @GetMapping("/api/users/owner-id")
    Integer getOwnerId(@RequestParam("accessToken") String accessToken, @RequestHeader(value = "X-Internal-Request") String internalRequest);

    @GetMapping("/api/users/profile/{userId}")
    public UserInfoResponse getUserInformation(@PathVariable Integer userId);

}