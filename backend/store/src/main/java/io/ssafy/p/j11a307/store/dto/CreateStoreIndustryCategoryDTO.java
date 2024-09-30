package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.IndustryCategory;
import io.ssafy.p.j11a307.store.entity.Store;
import io.ssafy.p.j11a307.store.entity.StoreIndustryCategory;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "StoreIndustryCategory를 생성하기 위한 DTO")
public record CreateStoreIndustryCategoryDTO(
        @Schema(description = "가게 ID", example = "1")
        Integer storeId,

        @Schema(description = "업종 카테고리 ID", example = "1")
        Integer industryCategoryId
) {
    // DTO에서 StoreIndustryCategory 엔티티로 변환하는 메서드
    public StoreIndustryCategory toEntity(Store store, IndustryCategory industryCategory) {
        return StoreIndustryCategory.builder()
                .store(store)
                .industryCategory(industryCategory)
                .build();
    }
}