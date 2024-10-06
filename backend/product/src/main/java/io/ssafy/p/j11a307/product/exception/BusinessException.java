package io.ssafy.p.j11a307.product.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Object detail;  // 추가 정보 필드

    // ErrorCode만 받는 생성자
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detail = null;  // detail이 없는 경우
    }

    // ErrorCode와 추가 정보를 받는 생성자
    public BusinessException(ErrorCode errorCode, Object detail) {
        super(String.format("%s: %s", errorCode.getMessage(), detail != null ? detail.toString() : "No details provided"));
        this.errorCode = errorCode;
        this.detail = detail;  // detail이 있는 경우
    }

    // ErrorCode 반환 메서드
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    // 추가 정보 반환 메서드 (optional)
    public Object getDetail() {
        return detail;
    }
}