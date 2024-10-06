package io.ssafy.p.j11a307.push_alert.controller;

import io.ssafy.p.j11a307.push_alert.dto.alerts.AlertType;
import io.ssafy.p.j11a307.push_alert.exception.BusinessException;
import io.ssafy.p.j11a307.push_alert.exception.ErrorCode;
import io.ssafy.p.j11a307.push_alert.service.AlertService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AlertController {

    @Value("{streat.internal-request}")
    private String internalRequestKey;

    private final AlertService alertService;

    @GetMapping(value = {"/order-accept", "/order-requested", "/cooking-completed", "/pickup-completed"})
    public void sendOrderStatusChangeAlert(
            Integer customerId, Integer orderId, String storeName,
            @RequestHeader(value = "X-Internal-Request") String internalRequest,
            HttpServletRequest request) {
        if (!internalRequestKey.equals(internalRequest)) {
            // MSA 내부 접속이 아닌 경우 에러 발생
            throw new BusinessException(ErrorCode.BAD_REQUEST);
        }
        String[] requestUri = request.getRequestURI().split("/");
        AlertType alertType = AlertType.getByRequestUri(requestUri[requestUri.length - 1]);
        alertService.sendOrderStatusChangeAlert(customerId, orderId, storeName, alertType);
    }

    @PostMapping("/dibs/{storeId}")
    public void subscribeStore(@PathVariable Integer storeId,
                               @RequestHeader(value = "X-Internal-Request") String internalRequest,
                               @RequestParam Integer userId) {
        if (!internalRequestKey.equals(internalRequest)) {
            throw new BusinessException(ErrorCode.BAD_REQUEST);
        }
        alertService.subscribeTopic(userId, storeId);
    }

    @DeleteMapping("/dibs/{storeId}")
    public void unsubscribeStore(@PathVariable Integer storeId,
                                 @RequestHeader(value = "X-Internal-Request") String internalRequest,
                                 @RequestParam Integer userId) {
        if (!internalRequestKey.equals(internalRequest)) {
            throw new BusinessException(ErrorCode.BAD_REQUEST);
        }
        alertService.unsubscribeTopic(userId, storeId);
    }
}
