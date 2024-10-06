package io.ssafy.p.j11a307.push_alert.controller;

import io.ssafy.p.j11a307.push_alert.dto.alerts.AlertType;
import io.ssafy.p.j11a307.push_alert.exception.BusinessException;
import io.ssafy.p.j11a307.push_alert.exception.ErrorCode;
import io.ssafy.p.j11a307.push_alert.service.AlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "주문 상태 변화 알림 전송", description = "주문 상태 변화 알림 전송")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공, 푸시 알림 전송 성공"),
            @ApiResponse(responseCode = "400", description = "내부에서만 알림 전송 요청 가능"),
            @ApiResponse(responseCode = "500", description = "푸시 알림 전송 실패")
    })
    @Parameters({
            @Parameter(name = "customerId", description = "알림을 받을 고객 아이디"),
            @Parameter(name = "orderId", description = "상태가 변경된 주문 아이디"),
            @Parameter(name = "storeName", description = "주문 점포명")
    })
    public void sendOrderStatusChangeAlert(
            @RequestParam Integer customerId,
            @RequestParam Integer orderId,
            @RequestParam String storeName,
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

    @GetMapping("/open-store")
    @Operation(summary = "점포 오픈 알림 전송", description = "점포 오픈 알림 전송")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공, 푸시 알림 전송 성공"),
            @ApiResponse(responseCode = "400", description = "내부에서만 알림 전송 요청 가능"),
            @ApiResponse(responseCode = "500", description = "푸시 알림 전송 실패")
    })
    @Parameters({
            @Parameter(name = "storeId", description = "점포 아이디"),
            @Parameter(name = "storeName", description = "점포명")
    })
    public void sendOpenStoreAlert(Integer storeId, String storeName, @RequestHeader(value = "X-Internal-Request") String internalRequest) {
        if (!internalRequestKey.equals(internalRequest)) {
            // MSA 내부 접속이 아닌 경우 에러 발생
            throw new BusinessException(ErrorCode.BAD_REQUEST);
        }
        alertService.sendOpenStoreAlert(storeId, storeName, AlertType.OPEN_STORE);
    }

    @PostMapping("/dibs/{storeId}")
    @GetMapping("/open-store")
    @Operation(summary = "점포 찜 등록", description = "점포 찜 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공, 푸시 알림 전송 성공"),
            @ApiResponse(responseCode = "400", description = "내부에서만 알림 전송 요청 가능"),
            @ApiResponse(responseCode = "500", description = "푸시 알림 전송 실패")
    })
    @Parameters({
            @Parameter(name = "storeId", description = "점포 아이디"),
            @Parameter(name = "userId", description = "유저 아이디")
    })
    public void subscribeStore(@PathVariable Integer storeId,
                               @RequestHeader(value = "X-Internal-Request") String internalRequest,
                               @RequestParam Integer userId) {
        if (!internalRequestKey.equals(internalRequest)) {
            throw new BusinessException(ErrorCode.BAD_REQUEST);
        }
        alertService.subscribeTopic(userId, storeId);
    }

    @DeleteMapping("/dibs/{storeId}")
    @PostMapping("/dibs/{storeId}")
    @GetMapping("/open-store")
    @Operation(summary = "점포 찜 취소", description = "점포 찜 취소")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공, 푸시 알림 전송 성공"),
            @ApiResponse(responseCode = "400", description = "내부에서만 알림 전송 요청 가능"),
            @ApiResponse(responseCode = "500", description = "푸시 알림 전송 실패")
    })
    @Parameters({
            @Parameter(name = "storeId", description = "점포 아이디"),
            @Parameter(name = "userId", description = "유저 아이디")
    })
    public void unsubscribeStore(@PathVariable Integer storeId,
                                 @RequestHeader(value = "X-Internal-Request") String internalRequest,
                                 @RequestParam Integer userId) {
        if (!internalRequestKey.equals(internalRequest)) {
            throw new BusinessException(ErrorCode.BAD_REQUEST);
        }
        alertService.unsubscribeTopic(userId, storeId);
    }
}
