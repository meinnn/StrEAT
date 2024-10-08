package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.Store;
import io.ssafy.p.j11a307.store.entity.StoreStatus;
import io.ssafy.p.j11a307.store.entity.StoreType;
import io.ssafy.p.j11a307.store.entity.SubCategory;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "가게 정보를 수정하기 위한 DTO")
public record UpdateStoreDTO(
        @Schema(description = "가게 ID", example = "1")
        Integer id,

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

        @Schema(description = "사업자 등록번호", example = "123-45-67890")
        String businessRegistrationNumber,

        @Schema(description = "하위 가게 카테고리ID", example = "30")
        Integer subCategoryId
) {
    // 기존 Store 엔티티를 받아서 업데이트할 엔티티로 변환하는 메서드
    public Store toEntity(Store existingStore) {
        return Store.builder()
                .id(existingStore.getId())
                .userId(existingStore.getUserId())
                .name(this.name != null ? this.name : existingStore.getName())
                .address(this.address != null ? this.address : existingStore.getAddress())
                .latitude(this.latitude != null ? this.latitude : existingStore.getLatitude())
                .longitude(this.longitude != null ? this.longitude : existingStore.getLongitude())
                .type(this.type != null ? this.type : existingStore.getType())
                .bankAccount(this.bankAccount != null ? this.bankAccount : existingStore.getBankAccount())
                .bankName(this.bankName != null ? this.bankName : existingStore.getBankName())
                .ownerWord(this.ownerWord != null ? this.ownerWord : existingStore.getOwnerWord())
                .storePhoneNumber(this.storePhoneNumber != null ? this.storePhoneNumber : existingStore.getStorePhoneNumber())
                .closedDays(this.closedDays != null ? this.closedDays : existingStore.getClosedDays())
                .status(this.status != null ? this.status : existingStore.getStatus())
                .businessRegistrationNumber(this.businessRegistrationNumber != null ? this.businessRegistrationNumber : existingStore.getBusinessRegistrationNumber())  // 사업자 등록번호 처리
                .subCategory(null)
                .build();
    }
}
