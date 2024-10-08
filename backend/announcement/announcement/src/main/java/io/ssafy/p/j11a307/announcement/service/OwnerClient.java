package io.ssafy.p.j11a307.announcement.service;

import io.ssafy.p.j11a307.announcement.dto.OwnerProfile;
import io.ssafy.p.j11a307.announcement.global.DataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

    @GetMapping("/api/users/announcement/information/{ownerId}")
    DataResponse<OwnerProfile> getAnnouncementOwnerInformation(@RequestHeader("X-Internal-Request") String internalRequest, @PathVariable Integer ownerId);
}
