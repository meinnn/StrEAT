package io.ssafy.p.j11a307.push_alert.dto;

import io.ssafy.p.j11a307.push_alert.entity.PushAlert;

public record PushAlertDetailResponse(
        Long id,
        Boolean checked,
        String createdAt,
        String title,
        String message,
        Integer orderId,
        Integer storeId
) {
    public PushAlertDetailResponse(PushAlert pushAlert) {
        this(pushAlert.getId(),
                pushAlert.getChecked(),
                pushAlert.getCreatedAt(),
                pushAlert.getTitle(),
                pushAlert.getMessage(),
                pushAlert.getOrderId(),
                pushAlert.getStoreId());
    }
}
