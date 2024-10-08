package io.ssafy.p.j11a307.payment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.ssafy.p.j11a307.payment.dto.PaymentResponse;
import io.ssafy.p.j11a307.payment.dto.TossPaymentBaseRequest;
import io.ssafy.p.j11a307.payment.dto.TossPaymentCancelRequest;
import io.ssafy.p.j11a307.payment.global.DataResponse;
import io.ssafy.p.j11a307.payment.global.MessageResponse;
import io.ssafy.p.j11a307.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/toss/request-payment")
    public ResponseEntity<DataResponse<PaymentResponse>> tossRequestPayment(TossPaymentBaseRequest tossPaymentBaseRequest) throws JsonProcessingException {
        PaymentResponse paymentResponse = paymentService.tossRequestPayment(tossPaymentBaseRequest);
        return ResponseEntity.status(HttpStatus.OK).body(DataResponse.of("결제 성공", paymentResponse));
    }

    @PostMapping("/toss/cancel-payment/{orderId}")
    public ResponseEntity<MessageResponse> cancelTossPayment(
            @PathVariable Integer orderId, @RequestBody TossPaymentCancelRequest tossPaymentCancelRequest) {

        paymentService.cancelTossPayment(orderId, tossPaymentCancelRequest);
        return ResponseEntity.status(HttpStatus.OK).body(MessageResponse.of("결제 취소 성공"));
    }
}
