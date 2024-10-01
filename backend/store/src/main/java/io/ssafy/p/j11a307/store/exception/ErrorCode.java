package io.ssafy.p.j11a307.store.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // Store
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 가게가 없습니다."),
    STORE_NAME_NULL(HttpStatus.BAD_REQUEST, "가게이름은 필수 입력 항목입니다. null 값을 허용하지 않습니다."),
    STORE_ADDRESS_NULL(HttpStatus.BAD_REQUEST, "가게주소는 필수 입력 항목입니다. null 값을 허용하지 않습니다."),
    // Store Location Photo
    STORE_LOCATION_PHOTO_LATITUDE_NULL(HttpStatus.BAD_REQUEST, "위도 값은 필수 입력 항목입니다. null 값을 허용하지 않습니다."),
    STORE_LOCATION_PHOTO_LONGITUDE_NULL(HttpStatus.BAD_REQUEST, "경도 값은 필수 입력 항목입니다. null 값을 허용하지 않습니다."),
    STORE_LOCATION_PHOTO_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 가게 위치 사진이 없습니다."),
    STORE_LOCATION_PHOTO_SRC_NULL(HttpStatus.BAD_REQUEST, "가게 위치 사진은 필수 입력 항목입니다. null 값을 허용하지 않습니다."),
    FILE_UPLOAD_FAIL(HttpStatus.BAD_REQUEST, "사진 업로드에 실패하였습니다."),
    INVALID_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "유효하지 않은 파일 확장자입니다."),
    //User
    INVALID_USER(HttpStatus.BAD_REQUEST, "해당하는 ID의 유저가 없습니다."),
    // Store Photo
    STORE_PHOTO_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 가게 사진이 없습니다."),
    // Product
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 d 없습니다."),
    PRODUCT_NAME_NULL(HttpStatus.BAD_REQUEST, "상품명은 필수 입력 항목입니다. null 값을 허용하지 않습니다."),
    INVALID_PRICE(HttpStatus.BAD_REQUEST, "가격은 null 이거나 음수 값을 허용하지 않습니다."),
    PRODUCT_SRC_NULL(HttpStatus.BAD_REQUEST, "상품이미지는 null 값을 허용하지 않습니다."),
    // Product Category
    PRODUCT_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 제품 카테고리가 없습니다."),
    PRODUCT_ID_NULL(HttpStatus.BAD_REQUEST, "Product ID는 필수 입력 항목입니다. null 값을 허용하지 않습니다."),
    PRODUCT_CATEGORY_NAME_NULL(HttpStatus.BAD_REQUEST, "카테고리명은 필수 입력 항목입니다. null 값을 허용하지 않습니다."),
    // Product Option Category
    PRODUCT_OPTION_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 제품 옵션 카테고리가 없습니다."),
    INVALID_MAX_SELECT(HttpStatus.BAD_REQUEST, "최대 선택 개수는 null 이거나 음수 값을 허용하지 않습니다."),
    PRODUCT_OPTION_CATEGORY_NAME_NULL(HttpStatus.BAD_REQUEST, "상품 옵션 카테고리명은 필수 입력 항목입니다. null 값을 허용하지 않습니다."),
    // Product Option
    PRODUCT_OPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 제품 옵션이 없습니다."),
    // Industry Category
    INDUSTRY_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 산업 카테고리가 없습니다."),
    INDUSTRY_CATEGORY_NAME_NULL(HttpStatus.BAD_REQUEST, "산업 카테고리명은 필수 입력 항목입니다. null 값을 허용하지 않습니다."),
    // Store Industry Category
    STORE_INDUSTRY_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "가게에 해당하는 ID의 산업 카테고리가 없습니다."),
    // Owner
    OWNER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 Owner가 없습니다."),
    // Internal Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생했습니다."),
    // Business Day
    BUSINESS_DAY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 영업일 정보가 없습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}

