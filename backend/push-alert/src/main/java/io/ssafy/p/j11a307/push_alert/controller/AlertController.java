package io.ssafy.p.j11a307.push_alert.controller;

import io.ssafy.p.j11a307.push_alert.dto.alerts.AlertType;
import io.ssafy.p.j11a307.push_alert.exception.BusinessException;
import io.ssafy.p.j11a307.push_alert.exception.ErrorCode;
import io.ssafy.p.j11a307.push_alert.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AlertController {

    @Value("{streat.internal-request}")
    private String internalRequestKey;

    private final AlertService alertService;

    @GetMapping("/order-reject")
    public void sendOrderRejectAlert(Integer customerId, Integer orderId, String storeName, @RequestHeader(value = "X-Internal-Request") String internalRequest) {
        if (!internalRequestKey.equals(internalRequest)) {
            // MSA 내부 접속이 아닌 경우 에러 발생
            throw new BusinessException(ErrorCode.BAD_REQUEST);
        }
        alertService.sendOrderStatusChangeAlert(customerId, orderId, storeName, AlertType.ORDER_ACCEPTED);
    }
}
