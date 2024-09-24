package io.ssafy.p.j11a307.gateway.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다.");

    private final HttpStatus status;
    private final String message;

    public int getStatus() {
        return status.value();
    }

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
