package io.ssafy.p.j11a307.push_alert.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 접근입니다."),
    PUSH_ALERT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "푸시 알림 전송에 실패했습니다."),
    ALERT_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, "푸시 알림 정보를 찾지 못했습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
