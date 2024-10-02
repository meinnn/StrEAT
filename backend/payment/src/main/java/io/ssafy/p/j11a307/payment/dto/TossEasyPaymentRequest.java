package io.ssafy.p.j11a307.payment.dto;

public record TossEasyPaymentRequest(
        String provider,
        Integer amount,
        Integer discountAmount
) {
}
