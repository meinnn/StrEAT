package io.ssafy.p.j11a307.payment.dto;

public record CancelDetail(
        String cancelReason,
        String canceledAt,
        Integer cancelAmount,
        Integer taxFreeAmount,
        Integer taxExemptionAmount,
        Integer refundableAmount,
        Integer easyPayDiscountAmount,
        String transactionKey,
        String receiptKey,
        String cancelStatus,
        String cancelRequestId
) {}
