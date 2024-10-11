package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.Store;
import io.ssafy.p.j11a307.store.entity.StoreStatus;
import io.ssafy.p.j11a307.store.entity.StoreType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "가게 정보를 반환하기 위한 DTO")
public record ReadStoreDTO(
        @Schema(description = "가게 ID", example = "1")
        Integer id,

        @Schema(description = "사용자 ID", example = "1")
        Integer userId,

        @Schema(description = "사업자 등록번호", example = "123-45-67890")
        String businessRegistrationNumber,

        @Schema(description = "가게명", example = "카페")
        String name,

        @Schema(description = "주소", example = "서울특별시 강남구")
        String address,

        @Schema(description = "위도", example = "37.123456")
        Double latitude,

        @Schema(description = "경도", example = "127.123456")
        Double longitude,

        @Schema(description = "업종 타입", example = "이동형")
        StoreType type,

        @Schema(description = "계좌번호", example = "1234-5678-9012-3456")
        String bankAccount,

        @Schema(description = "은행명", example = "신한은행")
        String bankName,

        @Schema(description = "사장님 한마디", example = "맛있게 드세요!")
        String ownerWord,

        @Schema(description = "가게 전화번호", example = "02-0000-0000")
        String storePhoneNumber,

        @Schema(description = "휴무일", example = "매주 월요일 휴무  09/16-09/18 추석 연휴 휴무")
        String closedDays,

        @Schema(description = "영업 상태", example = "영업중")
        StoreStatus status,

        @Schema(description = "하위 가게 카테고리ID", example = "30")
        Integer subCategoryId
) {
    // Store 엔티티를 받아서 DTO로 변환하는 생성자
    public ReadStoreDTO(Store store) {
        this(
                store.getId(),
                store.getUserId(),
                store.getBusinessRegistrationNumber(),
                store.getName(),
                store.getAddress(),
                store.getLatitude(),
                store.getLongitude(),
                store.getType(),
                store.getBankAccount(),
                store.getBankName(),
                store.getOwnerWord(),
                store.getStorePhoneNumber(),
                store.getClosedDays(),
                store.getStatus(),
                store.getSubCategory().getId()
        );
    }
}