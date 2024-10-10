package io.ssafy.p.j11a307.store.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // Store
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 가게가 없습니다."),
    STORE_NAME_NULL(HttpStatus.BAD_REQUEST, "가게이름은 필수 입력 항목입니다. null 값을 허용하지 않습니다."),
    STORE_ADDRESS_NULL(HttpStatus.BAD_REQUEST, "가게주소는 필수 입력 항목입니다. null 값을 허용하지 않습니다."),
    CLOSED_DAYS_NULL(HttpStatus.BAD_REQUEST,"휴무일이 null이거나 비어있습니다."),
    STORE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST,"해당 사용자ID로 이미 가게가 존재합니다."),
    OWNER_WORD_NULL(HttpStatus.BAD_REQUEST,"사장님 한마디가 null이거나 비어있습니다."),
    STORE_STATUS_NULL(HttpStatus.BAD_REQUEST,"가게 상태가 null이거나 비어있습니다."),
    STORE_LATITUDE_NULL(HttpStatus.BAD_REQUEST,"위도 값이 null이거나 비어있습니다."),
    STORE_LONGITUDE_NULL(HttpStatus.BAD_REQUEST,"경도 값이 null이거나 비어있습니다."),

    // Store Location Photo
    STORE_LOCATION_PHOTO_LATITUDE_NULL(HttpStatus.BAD_REQUEST, "위도 값은 필수 입력 항목입니다. null 값을 허용하지 않습니다."),
    STORE_LOCATION_PHOTO_LONGITUDE_NULL(HttpStatus.BAD_REQUEST, "경도 값은 필수 입력 항목입니다. null 값을 허용하지 않습니다."),
    STORE_LOCATION_PHOTO_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 가게 위치 사진이 없습니다."),
    STORE_LOCATION_PHOTO_SRC_NULL(HttpStatus.BAD_REQUEST, "가게 위치 사진은 필수 입력 항목입니다. null 값을 허용하지 않습니다."),
    FILE_UPLOAD_FAIL(HttpStatus.BAD_REQUEST, "사진 업로드에 실패하였습니다."),
    INVALID_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "해당하는 ID의 가게 간편 위치 정보가 없습니다."),
    //Store Location
    LOCATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당하는 ID의 가게 위치 정보가 없습니다."),
    UNAUTHORIZED_USER(HttpStatus.BAD_REQUEST, "간편 위치 저장 정보를 지울 권한이 없습니다."),
    //Store Simple Location
    SIMPLE_LOCATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당하는 ID의 가게 간편 위치 정보가 없습니다."),
    STORE_SIMPLE_PHOTO_NULL(HttpStatus.BAD_REQUEST, "가게 위치 사진은 필수 입력 항목입니다. null 값을 허용하지 않습니다."),
    STORE_MISMATCH(HttpStatus.BAD_REQUEST,"간편 위치 정보와 유저의 가게 정보가 일치하지 않습니다."),

    // User
    INVALID_USER(HttpStatus.BAD_REQUEST, "해당하는 ID의 유저가 없습니다."),

    // Store Photo
    STORE_PHOTO_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 가게 사진이 없습니다."),
    STORE_PHOTO_NULL(HttpStatus.NOT_FOUND, "가게사진은 필수 입력 항목입니다. null 값을 허용하지 않습니다."),
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
    // Sub Category
    SUB_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 가게 카테고리가 없습니다."),
    INDUSTRY_CATEGORY_NAME_NULL(HttpStatus.BAD_REQUEST, "산업 카테고리명은 필수 입력 항목입니다. null 값을 허용하지 않습니다."),
    // Store Industry Category
    STORE_INDUSTRY_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "가게에 해당하는 ID의 산업 카테고리가 없습니다."),
    // Owner
    OWNER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 Owner가 없습니다."),
    // Internal Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생했습니다."),
    // Business Day
    BUSINESS_DAY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 ID의 영업일 정보가 없습니다."),
    BUSINESS_DAY_ALREADY_EXISTS(HttpStatus.BAD_REQUEST,"이미 해당 가게에 영업일 정보가 존재합니다."),
    SRC_NOT_FOUND(HttpStatus.NOT_FOUND, "이미지가 존재하지 않습니다.");


    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}

