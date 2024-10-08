package io.ssafy.p.j11a307.push_alert.dto;

public record OrderStatusChangeRequest(
        Integer customerId,
        Integer orderId,
        String storeName,
        Integer storeId
) {}
