package io.ssafy.p.j11a307.payment.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 주문이 없습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
