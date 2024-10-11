package io.ssafy.p.j11a307.order.dto;

import lombok.Builder;

@Builder
public record OrderStatusChangeRequest(
        Integer customerId,
        Integer orderId,
        String storeName,
        Integer storeId
) {}
