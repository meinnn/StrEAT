package io.ssafy.p.j11a307.product.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;  // final 제거
    private Object detail;        // final 제거

    // 기존 생성자: ErrorCode만 받는 경우
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detail = null;  // 디테일 정보가 없는 경우
    }

    // 새로운 생성자: ErrorCode와 추가 정보를 함께 받는 경우
    public BusinessException(ErrorCode errorCode, final Object detail) {
        super(String.format("%s: %s", errorCode.getMessage(), detail != null ? detail.toString() : "No details provided"));
        this.errorCode = errorCode;
        this.detail = detail;  // id와 같은 추가 정보를 저장
    }
}
