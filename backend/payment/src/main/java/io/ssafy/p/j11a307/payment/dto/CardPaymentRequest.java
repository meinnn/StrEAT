package io.ssafy.p.j11a307.payment.dto;

public record CardPaymentRequest(
        String issuerCode,
        String acquireCode,
        String number,
        Integer installmentPlanMonths,
        Boolean isInterestFree,
        String interestPayer,
        String approveNo,
        Boolean useCardPoint,
        String cardType,
        String ownerType,
        String acquireStatus,
        Integer amount
) {
}
