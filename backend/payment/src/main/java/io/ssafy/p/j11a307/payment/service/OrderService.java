package io.ssafy.p.j11a307.payment.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ORDER")
public interface OrderService {

    @PostMapping("/complete-order")
    void completeOrder(Integer orderId, @RequestHeader("X-Internal-Request") String internalRequestHeader);
}
