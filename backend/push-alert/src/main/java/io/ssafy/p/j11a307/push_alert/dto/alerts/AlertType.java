package io.ssafy.p.j11a307.push_alert.dto.alerts;

import io.ssafy.p.j11a307.push_alert.exception.BusinessException;
import io.ssafy.p.j11a307.push_alert.exception.ErrorCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AlertType {
    ORDER_ACCEPTED("주문 수락! 맛있게 만들고 있어요", "order-accept");

    private final String message;
    private final String requestUri;

    AlertType(String message, String requestUri) {
        this.message = message;
        this.requestUri = requestUri;
    }

    public static AlertType getByRequestUri(String requestUri) {
        return Arrays.stream(AlertType.values())
                .filter(alertType -> alertType.requestUri.equals(requestUri))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.ALERT_TYPE_NOT_FOUND));
    }
}
