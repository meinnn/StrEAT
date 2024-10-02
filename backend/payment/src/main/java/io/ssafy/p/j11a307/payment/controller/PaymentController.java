package io.ssafy.p.j11a307.payment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.ssafy.p.j11a307.payment.dto.TossPaymentBaseRequest;
import io.ssafy.p.j11a307.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/toss/request-payment")
    public ResponseEntity<Void> tossRequestPayment(TossPaymentBaseRequest tossPaymentBaseRequest) throws JsonProcessingException {
        paymentService.tossRequestPayment(tossPaymentBaseRequest);
        return ResponseEntity.ok().build();
    }
}
