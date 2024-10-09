package io.ssafy.p.j11a307.user.service;

import io.ssafy.p.j11a307.user.dto.ReviewAveragesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("order")
public interface ReviewService {

    @GetMapping("/api/orders/review-list")
    ReviewAveragesResponse getReviewAverageList(@RequestParam("storeIdList") List<Integer> storeIdList,
                                                @RequestHeader(value = "X-Internal-Request") String internalRequest);
}
