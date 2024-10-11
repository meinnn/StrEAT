package io.ssafy.p.j11a307.push_alert.dto.alerts;

import io.ssafy.p.j11a307.push_alert.exception.BusinessException;
import io.ssafy.p.j11a307.push_alert.exception.ErrorCode;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AlertType {
    ORDER_ACCEPTED("\uD83C\uDF73 주문 수락! 맛있게 만들고 있어요", "order-accept"),
    ORDER_REQUESTED("⏳ 주문 요청 완료! 사장님 수락 후 조리가 시작돼요", "order-requested"),
    COOKING_COMPLETED("\uD83C\uDF7D\uFE0F 메뉴 조리 완료! 픽업을 기다리고 있어요", "cooking-completed"),
    PICKUP_COMPLETED("\uD83D\uDE4C 픽업 완료! 맛있게 드세요", "pickup-completed"),
    OPEN_STORE("\uD83D\uDD14 주변에 단골 가게가 영업 중이에요!", "open-store");

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
