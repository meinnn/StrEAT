package io.ssafy.p.j11a307.payment.controller;

import io.ssafy.p.j11a307.payment.dto.TossPaymentBaseRequest;
import io.ssafy.p.j11a307.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/toss/request-payment")
    public String tossRequestPayment(TossPaymentBaseRequest tossPaymentBaseRequest) {
        paymentService.tossRequestPayment(tossPaymentBaseRequest);
    }
}
