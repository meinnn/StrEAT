package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.StoreIndustryCategory;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "StoreIndustryCategory 정보를 반환하기 위한 DTO")
public record ReadStoreIndustryCategoryDTO(
        @Schema(description = "StoreIndustryCategory ID", example = "1")
        Integer id,

        @Schema(description = "가게 ID", example = "1")
        Integer storeId,

        @Schema(description = "업종 카테고리 ID", example = "1")
        Integer industryCategoryId
) {
    // StoreIndustryCategory 엔티티를 받아서 DTO로 변환하는 생성자
    public ReadStoreIndustryCategoryDTO(StoreIndustryCategory storeIndustryCategory) {
        this(
                storeIndustryCategory.getId(),
                storeIndustryCategory.getStore().getId(),
                storeIndustryCategory.getIndustryCategory().getId()
        );
    }
}