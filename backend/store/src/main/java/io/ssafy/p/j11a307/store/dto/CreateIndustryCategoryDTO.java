package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.IndustryCategory;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "업종을 생성하기 위한 DTO")
public record CreateIndustryCategoryDTO(
        @Schema(description = "업종명", example = "음식점")
        String name
) {
    // DTO에서 IndustryCategory 엔티티로 변환하는 메서드
    public IndustryCategory toEntity() {
        return IndustryCategory.builder()
                .name(this.name)
                .build();
    }
}