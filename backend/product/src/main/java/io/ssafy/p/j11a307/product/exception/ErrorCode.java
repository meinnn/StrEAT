package io.ssafy.p.j11a307.product.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // Store
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 가게가 없습니다."),
    // Product
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 물건이 없습니다."),
    // Owner
    OWNER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 Owner가 없습니다."),
    // Internal Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}

