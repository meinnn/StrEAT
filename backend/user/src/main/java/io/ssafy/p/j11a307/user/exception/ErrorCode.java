package io.ssafy.p.j11a307.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 유저가 없습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "로그인 기간이 만료되었습니다. 다시 로그인 해주세요."),
    ALREADY_REGISTERED_OWNER(HttpStatus.BAD_REQUEST, "이미 사장님으로 회원가입 했습니다."),
    ALREADY_REGISTERED_CUSTOMER(HttpStatus.BAD_REQUEST, "이미 손님으로 회원가입 했습니다."),
    OWNER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 id로 가입된 사장님이 없습니다."),
    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 id로 가입된 고객이 없습니다."),
    BAD_INNER_SERVICE_REQUEST(HttpStatus.BAD_REQUEST, "내부 서비스에서만 요청할 수 있습니다."),
    BAD_USER_TYPE_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 유저 타입 요청입니다."),
    NOT_SUBSCRIBED_STORE(HttpStatus.BAD_REQUEST, "찜한 가게가 아닙니다. 알림을 켜고 끌 수 없습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
