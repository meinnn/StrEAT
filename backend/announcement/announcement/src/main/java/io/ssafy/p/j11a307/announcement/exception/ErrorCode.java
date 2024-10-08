package io.ssafy.p.j11a307.announcement.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생했습니다."),
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED,"권한이 없는 유저입니다." ),
    SUBMIT_FILE_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "파일 입력 자동화 과정에서 에러가 발생했습니다."),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "파일이 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}


