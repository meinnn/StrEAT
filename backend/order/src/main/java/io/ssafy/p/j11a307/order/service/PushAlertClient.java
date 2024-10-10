package io.ssafy.p.j11a307.order.service;

import io.ssafy.p.j11a307.order.dto.OrderStatusChangeRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "push-alert")
public interface PushAlertClient {

    @GetMapping("/api/push-alert/order-accept")
    void sendOrderAcceptedAlert(@RequestParam OrderStatusChangeRequest orderStatusChangeRequest,
                                    @RequestHeader(value = "X-Internal-Request") String internalRequest);

    @GetMapping("/api/push-alert/cooking-completed")
    void sendCookingCompletedAlert(@RequestParam OrderStatusChangeRequest orderStatusChangeRequest,
                                   @RequestHeader(value = "X-Internal-Request") String internalRequest);
}
