package io.ssafy.p.j11a307.push_alert.dto.alerts;

import lombok.Getter;

@Getter
public enum AlertType {
    ORDER_ACCEPTED("주문 수락! 맛있게 만들고 있어요");

    private final String message;

    AlertType(String message) {
        this.message = message;
    }
}
