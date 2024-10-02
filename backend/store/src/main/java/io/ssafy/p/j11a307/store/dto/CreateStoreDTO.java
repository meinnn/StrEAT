package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.IndustryCategory;
import io.ssafy.p.j11a307.store.entity.Store;
import io.ssafy.p.j11a307.store.entity.StoreStatus;
import io.ssafy.p.j11a307.store.entity.StoreType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "가게를 생성하기 위한 DTO")
public record CreateStoreDTO(
        @Schema(description = "사업자 등록번호", example = "123-45-67890")
        String businessRegistrationNumber,

        @Schema(description = "가게명", example = "카페")
        String name,

        @Schema(description = "주소", example = "서울특별시 강남구")
        String address,

        @Schema(description = "위도", example = "37.123456")
        String latitude,

        @Schema(description = "경도", example = "127.123456")
        String longitude,

        @Schema(description = "업종 타입", example = "이동형")
        String type,

        @Schema(description = "계좌번호", example = "1234-5678-9012-3456")
        String bankAccount,

        @Schema(description = "은행명", example = "신한은행")
        String bankName,

        @Schema(description = "사장님 한마디", example = "맛있게 드세요!")
        String ownerWord,

        @Schema(description = "가게 전화번호", example = "02-0000-0000")
        String storePhoneNumber,

        @Schema(description = "영업 상태", example = "영업중")
        String status,

        @Schema(description = "업종 카테고리 ID", example = "1")
        Integer industryCategoryId

) {

    // DTO에서 Store 엔티티로 변환하는 메서드
    public Store toEntity(IndustryCategory industryCategory) {
        return Store.builder()
                .businessRegistrationNumber(this.businessRegistrationNumber)
                .name(this.name)
                .address(this.address)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .type(StoreType.fromDescription(this.type))  // StoreType 변환
                .bankAccount(this.bankAccount)
                .bankName(this.bankName)
                .ownerWord(this.ownerWord)
                .storePhoneNumber(this.storePhoneNumber)  // storePhoneNumber 추가
                .status(StoreStatus.fromDescription(this.status))  // StoreStatus 변환
                .industryCategory(industryCategory)  // IndustryCategory 설정
                .build();
    }
}