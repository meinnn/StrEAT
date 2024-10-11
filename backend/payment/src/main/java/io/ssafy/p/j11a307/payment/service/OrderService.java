package io.ssafy.p.j11a307.payment.service;

import io.ssafy.p.j11a307.payment.dto.DataResponseFromOtherService;
import io.ssafy.p.j11a307.payment.dto.MessageResponseFromOtherService;
import io.ssafy.p.j11a307.payment.dto.PayProcessRequest;
import io.ssafy.p.j11a307.payment.global.MessageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ORDER")
public interface OrderService {

    @PostMapping("/api/orders/order-request/pay-complete")
    DataResponseFromOtherService<Integer> completeOrder(@RequestBody PayProcessRequest payProcessRequest,
                                               @RequestHeader("X-Internal-Request") String internalRequestHeader);
}
