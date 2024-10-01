package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.IndustryCategory;
import io.ssafy.p.j11a307.store.entity.Store;
import io.ssafy.p.j11a307.store.entity.StoreIndustryCategory;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "StoreIndustryCategory를 수정하기 위한 DTO")
public record UpdateStoreIndustryCategoryDTO(
        @Schema(description = "StoreIndustryCategory ID", example = "1")
        Integer id,

        @Schema(description = "가게 ID", example = "1")
        Integer storeId,

        @Schema(description = "업종 카테고리 ID", example = "2")
        Integer industryCategoryId
) {
    // DTO에서 StoreIndustryCategory 엔티티로 변환하는 메서드
    public StoreIndustryCategory toEntity(StoreIndustryCategory existingStoreIndustryCategory, Store store, IndustryCategory industryCategory) {
        return StoreIndustryCategory.builder()
                .id(existingStoreIndustryCategory.getId()) // 기존 ID 유지
                .store(store != null ? store : existingStoreIndustryCategory.getStore()) // 새로운 가게가 전달되지 않으면 기존 값을 유지
                .industryCategory(industryCategory != null ? industryCategory : existingStoreIndustryCategory.getIndustryCategory()) // 새로운 카테고리가 전달되지 않으면 기존 값을 유지
                .build();
    }
}