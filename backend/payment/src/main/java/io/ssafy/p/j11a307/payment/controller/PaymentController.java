package io.ssafy.p.j11a307.payment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.ssafy.p.j11a307.payment.dto.PaymentResponse;
import io.ssafy.p.j11a307.payment.dto.TossPaymentBaseRequest;
import io.ssafy.p.j11a307.payment.dto.TossPaymentCancelRequest;
import io.ssafy.p.j11a307.payment.global.DataResponse;
import io.ssafy.p.j11a307.payment.global.MessageResponse;
import io.ssafy.p.j11a307.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/toss/request-payment")
    @Operation(summary = "토스 결제 요청", description = "토스 결제 요청")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토스 결제 요청",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentResponse.class))),
            @ApiResponse(responseCode = "404", description = "해당 id 유저 없음")
    })
    @Parameters({
            @Parameter(name = "paymentKey", description = "toss payments가 제공하는 paymentKey"),
            @Parameter(name = "orderId", description = "order id"),
            @Parameter(name = "amount", description = "결제 금액")
    })
    public ResponseEntity<DataResponse<PaymentResponse>> tossRequestPayment(@RequestBody TossPaymentBaseRequest tossPaymentBaseRequest) throws JsonProcessingException {
        log.info("request orderId: {}", tossPaymentBaseRequest.orderId());
        PaymentResponse paymentResponse = paymentService.tossRequestPayment(tossPaymentBaseRequest);
        if (paymentResponse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("response orderId: {}", paymentResponse.orderId());
        return ResponseEntity.status(HttpStatus.OK).body(DataResponse.of("결제 성공", paymentResponse));
    }

    @PostMapping("/toss/cancel-payment/{orderId}")
    @Operation(summary = "토스 결제 취소 요청", description = "토스 결제 취소 요청")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토스 결제 취소 요청"),
            @ApiResponse(responseCode = "404", description = "해당 id 유저 없음")
    })
    @Parameters({
            @Parameter(name = "cancelReason", description = "취소 사유")
    })
    public ResponseEntity<MessageResponse> cancelTossPayment(
            @PathVariable String orderId, @RequestBody TossPaymentCancelRequest tossPaymentCancelRequest) {

        paymentService.cancelTossPayment(orderId, tossPaymentCancelRequest);
        return ResponseEntity.status(HttpStatus.OK).body(MessageResponse.of("결제 취소 성공"));
    }
}
