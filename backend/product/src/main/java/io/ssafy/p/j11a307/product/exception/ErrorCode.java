package io.ssafy.p.j11a307.product.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // Store
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 가게가 없습니다."),
    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 유저가 없습니다."),
    // Product
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 상품이 없습니다."),
    PRODUCT_EMPTY(HttpStatus.BAD_REQUEST, "상품 객체가 비어있습니다."),
    PRODUCT_NAME_NULL(HttpStatus.BAD_REQUEST, "상품명은 필수 입력 항목입니다. null 값을 허용하지 않습니다."),
    PRODUCT_PRICE_NULL(HttpStatus.BAD_REQUEST, "상품가격은 필수 입력 항목입니다. null 값을 허용하지 않습니다."),
    INVALID_PRICE(HttpStatus.BAD_REQUEST, "가격은 null 이거나 음수 값을 허용하지 않습니다."),
    PRODUCT_PHOTO_NULL(HttpStatus.BAD_REQUEST, "상품이미지는 null 값을 허용하지 않습니다."),
    FILE_UPLOAD_FAIL(HttpStatus.BAD_REQUEST, "사진 업로드에 실패하였습니다."),
    INVALID_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "유효하지 않은 파일 확장자입니다."),
    PRODUCT_DESCRIPTION_NULL(HttpStatus.BAD_REQUEST, "상품설명은 필수 입력 항목입니다. null 값을 허용하지 않습니다."),
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "해당 상품의 가게 ID와 사용자의 가게 ID가 일치하지 않습니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST,"상품명, 상품설명, 가격의 입력값이 각각 String, String, Integer 인지 확인해주세요."),
    PRODUCT_IMAGES_NULL(HttpStatus.BAD_REQUEST,"상품 이미지가 비어있습니다."),
    // Product Photo
    PRODUCT_PHOTO_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 상품 사진이 없습니다."),
    // Product Category
    PRODUCT_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 제품 카테고리가 없습니다."),
    PRODUCT_CATEGORY_EMPTY(HttpStatus.NOT_FOUND, "제품 카테고리가 비어있습니다."),
    PRODUCT_ID_NULL(HttpStatus.BAD_REQUEST, "Product ID는 필수 입력 항목입니다. null 값을 허용하지 않습니다."),
    PRODUCT_CATEGORY_NAME_NULL(HttpStatus.BAD_REQUEST, "카테고리명은 필수 입력 항목입니다. null 값을 허용하지 않습니다."),
    // Product Option Category
    PRODUCT_OPTION_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 제품 옵션 카테고리가 없습니다."),
    PRODUCT_OPTION_CATEGORY_EMPTY(HttpStatus.NOT_FOUND, "제품 옵션 카테고리가 비어있습니다."),
    INVALID_MAX_SELECT(HttpStatus.BAD_REQUEST, "최대 선택 개수는 null 이거나 음수 값을 허용하지 않습니다."),
    INVALID_MIN_SELECT(HttpStatus.BAD_REQUEST, "최대 선택 개수는 null 이거나 음수 값을 허용하지 않습니다."),
    PRODUCT_OPTION_CATEGORY_NAME_NULL(HttpStatus.BAD_REQUEST, "상품 옵션 카테고리명은 필수 입력 항목입니다. null 값을 허용하지 않습니다."),
    // Product Option
    PRODUCT_OPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 제품 옵션이 없습니다."),
    PRODUCT_OPTION_EMPTY(HttpStatus.NOT_FOUND, "제품 옵션이 비어있습니다."),
    PRODUCT_OPTION_NAME_NULL(HttpStatus.NOT_FOUND, "제품 옵션 이름이 비어있습니다."),
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

