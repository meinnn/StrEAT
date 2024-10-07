package io.ssafy.p.j11a307.payment.dto;

import java.util.List;

public record TossPaymentCancelResponse(
        String mId,
        String version,
        String lastTransactionKey,
        String paymentKey,
        String orderId,
        String orderName,
        String currency,
        String method,
        String status,
        List<CancelDetail> cancels,  // cancels 필드
        String secret,
        String type,
        String easyPay,
        String country,
        Integer totalAmount,
        Integer balanceAmount,
        Integer suppliedAmount,
        Integer vat,
        Integer taxFreeAmount,
        Integer taxExemptionAmount
) {}
