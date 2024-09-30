package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.IndustryCategory;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "업종 정보를 반환하기 위한 DTO")
public record ReadIndustryCategoryDTO(
        @Schema(description = "업종 ID", example = "1")
        Integer id,

        @Schema(description = "업종명", example = "음식점")
        String name
) {
    // IndustryCategory 엔티티를 받아서 DTO로 변환하는 생성자
    public ReadIndustryCategoryDTO(IndustryCategory industryCategory) {
        this(
                industryCategory.getId(),
                industryCategory.getName()
        );
    }
}