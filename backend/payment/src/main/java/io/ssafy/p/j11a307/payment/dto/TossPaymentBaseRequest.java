package io.ssafy.p.j11a307.payment.dto;

public record TossPaymentBaseRequest(
        String paymentKey,
        String orderId,
        String amount
) {
}
