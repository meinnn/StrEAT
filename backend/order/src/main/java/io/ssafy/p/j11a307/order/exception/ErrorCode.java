package io.ssafy.p.j11a307.order.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생했습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 주문 내역을 찾을 수 없습니다."),
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED,"권한이 없는 유저입니다." ),
    REVIEW_DUPLICATED(HttpStatus.BAD_REQUEST, "이미 리뷰를 작성한 이력이 있습니다."),
    FileEmptyException(HttpStatus.BAD_REQUEST, "파일이 비었습니다." ),
    S3Exception(HttpStatus.BAD_REQUEST, "이미지 업로드 중 유효하지 않은 형식으로 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}


