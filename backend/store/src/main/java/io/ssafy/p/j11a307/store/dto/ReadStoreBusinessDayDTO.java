package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.BusinessDay;
import io.ssafy.p.j11a307.store.entity.Store;
import io.ssafy.p.j11a307.store.entity.StoreStatus;
import io.ssafy.p.j11a307.store.entity.StoreType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "가게 정보와 영업일 정보를 포함하는 DTO")
public record ReadStoreBusinessDayDTO(
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

        @Schema(description = "업종 타입", example = "이동형")
        StoreType type,

        @Schema(description = "영업 상태", example = "영업중")
        StoreStatus status,

        @Schema(description = "가게 전화번호", example = "02-0000-0000")
        String storePhoneNumber,

        @Schema(description = "휴무일", example = "매주 월요일 휴무")
        String closedDays,

        @Schema(description = "가게 영업일 정보")
        BusinessDay businessDay  // 가게의 영업일 정보
) {
    public ReadStoreBusinessDayDTO(Store store, BusinessDay businessDay) {
        this(
                store.getId(),
                store.getUserId(),
                store.getBusinessRegistrationNumber(),
                store.getName(),
                store.getAddress(),
                store.getType(),
                store.getStatus(),
                store.getStorePhoneNumber(),
                store.getClosedDays(),
                businessDay  // 가게의 단일 영업일 정보
        );
    }
}
