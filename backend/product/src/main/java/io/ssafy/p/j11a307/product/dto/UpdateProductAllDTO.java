package io.ssafy.p.j11a307.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "상품 전체 정보를 업데이트하기 위한 DTO")
public record UpdateProductAllDTO(

        @Schema(description = "상품명", example = "타코야끼")
        String name,

        @Schema(description = "상품 설명", example = "맛있는 타코야끼")
        String description,

        @Schema(description = "상품 가격", example = "5000")
        Integer price,

        @Schema(description = "상품 카테고리 목록 (예: 한식, 중식)")
        Integer categoryId,

        @Schema(description = "상품 옵션 카테고리 목록 (예: 사이즈, 맛 선택)")
        List<UpdateProductOptionCategoryWithoutProductIdDTO> optionCategories
) {
}