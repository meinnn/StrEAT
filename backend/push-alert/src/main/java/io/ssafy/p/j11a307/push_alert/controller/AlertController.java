package io.ssafy.p.j11a307.push_alert.controller;

import io.ssafy.p.j11a307.push_alert.dto.GlobalDibsAlertRequest;
import io.ssafy.p.j11a307.push_alert.dto.OrderStatusChangeRequest;
import io.ssafy.p.j11a307.push_alert.dto.PushAlertHistoryResponse;
import io.ssafy.p.j11a307.push_alert.dto.alerts.AlertType;
import io.ssafy.p.j11a307.push_alert.exception.BusinessException;
import io.ssafy.p.j11a307.push_alert.exception.ErrorCode;
import io.ssafy.p.j11a307.push_alert.global.DataResponse;
import io.ssafy.p.j11a307.push_alert.global.MessageResponse;
import io.ssafy.p.j11a307.push_alert.service.AlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AlertController {

    @Value("${streat.internal-request}")
    private String internalRequestKey;

    private final String HEADER_AUTH = "Authorization";

    private final AlertService alertService;

    @PostMapping(value = {"/order-accept", "/order-requested", "/cooking-completed", "/pickup-completed"})
    @Operation(summary = "주문 상태 변화 알림 전송", description = "주문 상태 변화 알림 전송")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공, 푸시 알림 전송 성공"),
            @ApiResponse(responseCode = "400", description = "내부에서만 알림 전송 요청 가능"),
            @ApiResponse(responseCode = "500", description = "푸시 알림 전송 실패")
    })
    @Parameters({
            @Parameter(name = "customerId", description = "알림을 받을 고객 아이디"),
            @Parameter(name = "orderId", description = "상태가 변경된 주문 아이디"),
            @Parameter(name = "storeName", description = "주문 점포명"),
            @Parameter(name = "storeId", description = "점포 아이디")
    })
    public void sendOrderStatusChangeAlert(
            @RequestBody OrderStatusChangeRequest orderStatusChangeRequest,
            @RequestHeader(value = "X-Internal-Request") String internalRequest,
            HttpServletRequest request) {
        if (!internalRequestKey.equals(internalRequest)) {
            // MSA 내부 접속이 아닌 경우 에러 발생
            throw new BusinessException(ErrorCode.BAD_REQUEST);
        }
        String[] requestUri = request.getRequestURI().split("/");
        AlertType alertType = AlertType.getByRequestUri(requestUri[requestUri.length - 1]);
        alertService.sendOrderStatusChangeAlert(orderStatusChangeRequest, alertType);
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
    public void sendOpenStoreAlert(@RequestParam Integer storeId, @RequestParam String storeName,
                                   @RequestHeader(value = "X-Internal-Request") String internalRequest) {
        if (!internalRequestKey.equals(internalRequest)) {
            // MSA 내부 접속이 아닌 경우 에러 발생
            throw new BusinessException(ErrorCode.BAD_REQUEST);
        }
        alertService.sendOpenStoreAlert(storeId, storeName, AlertType.OPEN_STORE);
    }

    @PostMapping("/dibs/{storeId}")
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
        alertService.subscribeToStore(userId, storeId);
    }

    @DeleteMapping("/dibs/{storeId}")
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
        alertService.unsubscribeFromStore(userId, storeId);
    }

    @PostMapping("/check/{alertId}")
    @Operation(summary = "푸시 알림 확인", description = "푸시 알림 확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "푸시 알림 확인 성공"),
            @ApiResponse(responseCode = "404", description = "푸시 알림을 찾지 못했습니다")
    })
    public ResponseEntity<MessageResponse> checkAlert(@PathVariable Long alertId) {
        alertService.checkAlert(alertId);
        return ResponseEntity.status(HttpStatus.OK).body(MessageResponse.of("푸시 알림 확인 성공"));
    }

    @GetMapping("/all")
    @Operation(summary = "전체 푸시 알림 목록 조회", description = "전체 푸시 알림 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "푸시 알림 목록 조회 성공")
    })
    public ResponseEntity<DataResponse<PushAlertHistoryResponse>> getAllPushAlerts(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") Integer pgno,
            @RequestParam(defaultValue = "10") Integer spp) {
        String accessToken = request.getHeader(HEADER_AUTH);
        PushAlertHistoryResponse pushAlertHistoryResponse = alertService.getAllAlertsByUserId(accessToken, pgno, spp);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponse.of("전체 푸시 알림 목록 조회 성공", pushAlertHistoryResponse));
    }

    @PostMapping("/global/dibs-alert")
    @Operation(summary = "전체 찜 알림 on", description = "전체 찜 알림 on")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "전체 찜 알림 on 성공"),
            @ApiResponse(responseCode = "400", description = "내부에서만 요청 가능")
    })
    @Tag(name = "내부 서비스 간 요청")
    public ResponseEntity<MessageResponse> turnOnAllDibsAlerts(
            @RequestBody GlobalDibsAlertRequest globalDibsAlertRequest,
            @RequestHeader(value = "X-Internal-Request") String internalRequest) {
        if (!internalRequestKey.equals(internalRequest)) {
            throw new BusinessException(ErrorCode.BAD_REQUEST);
        }
        String fcmToken = globalDibsAlertRequest.fcmToken();
        List<Integer> storeIds = globalDibsAlertRequest.storeIds();
        alertService.turnOnAllDibsAlerts(fcmToken, storeIds);
        return ResponseEntity.status(HttpStatus.OK).body(MessageResponse.of("전체 찜 알림을 켰습니다."));
    }

    @DeleteMapping("/global/dibs-alert")
    @Operation(summary = "전체 찜 알림 off", description = "전체 찜 알림 off")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "전체 찜 알림 on 성공"),
            @ApiResponse(responseCode = "400", description = "내부에서만 요청 가능")
    })
    @Tag(name = "내부 서비스 간 요청")
    public ResponseEntity<MessageResponse> turnOffAllDibsAlerts(
            @RequestBody GlobalDibsAlertRequest globalDibsAlertRequest,
            @RequestHeader(value = "X-Internal-Request") String internalRequest) {

        if (!internalRequestKey.equals(internalRequest)) {
            throw new BusinessException(ErrorCode.BAD_REQUEST);
        }
        String fcmToken = globalDibsAlertRequest.fcmToken();
        List<Integer> storeIds = globalDibsAlertRequest.storeIds();
        alertService.turnOffAllDibsAlerts(fcmToken, storeIds);
        return ResponseEntity.status(HttpStatus.OK).body(MessageResponse.of("전체 찜 알림을 껐습니다."));
    }
}
