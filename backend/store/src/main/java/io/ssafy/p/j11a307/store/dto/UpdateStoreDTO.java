package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.Store;
import io.ssafy.p.j11a307.store.entity.StoreStatus;
import io.ssafy.p.j11a307.store.entity.StoreType;
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
        String latitude,

        @Schema(description = "경도", example = "127.123456")
        String longitude,

        @Schema(description = "업종 타입", example = "카페")
        StoreType type,

        @Schema(description = "계좌번호", example = "1234-5678-9012-3456")
        String bankAccount,

        @Schema(description = "은행명", example = "신한은행")
        String bankName,

        @Schema(description = "사장님 한마디", example = "맛있게 드세요!")
        String ownerWord,

        @Schema(description = "영업 상태", example = "영업 중")
        StoreStatus status,

        @Schema(description = "업종 카테고리 ID", example = "레스토랑")
        Integer industryCategoryId


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
                .status(this.status != null ? this.status : existingStore.getStatus())
                .industryCategory(null)
                .build();
    }
}