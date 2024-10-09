package io.ssafy.p.j11a307.product.dto;

import io.ssafy.p.j11a307.product.entity.ProductOptionCategory;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "옵션 카테고리를 조회하기 위한 DTO")
public record ReadProductOptionCategoryDTO(

        @Schema(description = "옵션 카테고리 ID", example = "1")
        Integer id,

        @Schema(description = "상품 ID", example = "1")
        Integer productId,

        @Schema(description = "옵션 카테고리명", example = "맛고르기")
        String name,

        @Schema(description = "필수 여부", example = "true")
        Boolean isEssential,

        @Schema(description = "최대 선택 개수", example = "3")
        Integer maxSelect,

        @Schema(description = "최소 선택 개수", example = "1")
        Integer minSelect,

        @Schema(description = "옵션 목록")
        List<ReadProductOptionDTO> options
) {
    // ProductOptionCategory 엔티티를 받아서 DTO로 변환하는 생성자
    public ReadProductOptionCategoryDTO(ProductOptionCategory optionCategory) {
        this(
                optionCategory.getId(),
                optionCategory.getProduct().getId(),
                optionCategory.getName(),
                optionCategory.getIsEssential(),
                optionCategory.getMaxSelect(),
                optionCategory.getMinSelect(),
                optionCategory.getOptions().stream()
                        .map(ReadProductOptionDTO::new)
                        .toList()
        );
    }
}
