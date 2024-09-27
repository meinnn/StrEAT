package io.ssafy.p.j11a307.gateway.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "로그인 기간이 만료되었습니다. 다시 로그인 해주세요.");

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
