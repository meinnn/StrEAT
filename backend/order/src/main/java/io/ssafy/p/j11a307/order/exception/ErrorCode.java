package io.ssafy.p.j11a307.order.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생했습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 주문 내역을 찾을 수 없습니다."),
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED,"권한이 없는 유저입니다." ),
    REVIEW_DUPLICATED(HttpStatus.BAD_REQUEST, "이미 리뷰를 작성한 이력이 있습니다."),
    FileEmptyException(HttpStatus.BAD_REQUEST, "파일이 비었습니다." ),
    S3Exception(HttpStatus.BAD_REQUEST, "이미지 업로드 중 유효하지 않은 형식으로 오류가 발생했습니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 리뷰를 찾을 수 없습니다."),
    REVIEW_ALREADY_FOUND(HttpStatus.BAD_REQUEST, "이미 리뷰가 존재합니다." ),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 상품이 존재하지 않습니다."),
    PRODUCT_OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "상품 재고가 없습니다."),
    WRONG_PRODUCT_OPTION(HttpStatus.BAD_REQUEST, "상품의 유효한 옵션이 아닙니다." ),
    SHOPPING_CART_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 장바구니 내역이 존재하지 않습니다." ),
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 점포가 존재하지 않습니다."),
    WRONG_STATUS(HttpStatus.BAD_REQUEST, "올바른 Status 정보를 입력해주세요."),
    WRONG_ORDER_ID(HttpStatus.BAD_REQUEST, "주문이 유효하지 않은 상태입니다."),
    WRONG_FLAG(HttpStatus.BAD_REQUEST,"올바른 flag 정보를 입력해주세요."),
    WRONG_SEARCHTIME(HttpStatus.BAD_REQUEST, "올바른 검색 시간 정보를 입력하세요." ),
    PHOTO_NOT_FOUND(HttpStatus.BAD_REQUEST, "사진을 찾을 수 없습니다. 존재하지 않는다면 디폴트 사진을 넣어주세요."),
    WRONG_PAYTYPECODE(HttpStatus.BAD_REQUEST,"지원하지 않는 결제 방식입니다." ),
    ALREADY_DONE(HttpStatus.BAD_REQUEST, "이미 처리된 결제입니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}


