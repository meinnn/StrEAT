package io.ssafy.p.j11a307.product.dto;

import io.ssafy.p.j11a307.product.entity.Product;
import io.ssafy.p.j11a307.product.entity.ProductOption;
import io.ssafy.p.j11a307.product.entity.ProductOptionCategory;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "상품 옵션을 생성하기 위한 DTO")
public record CreateProductOptionDTO(
        @Schema(description = "상품 ID", example = "1")
        Integer productId,

        @Schema(description = "상품 옵션 카테고리 ID", example = "1")
        Integer productOptionCategoryId,

        @Schema(description = "옵션 설명", example = "매운맛")
        String description
) {
    // DTO에서 ProductOption 엔티티로 변환하는 메서드
    public ProductOption toEntity(Product product, ProductOptionCategory productOptionCategory) {
        return ProductOption.builder()
                .product(product)
                .productOptionCategory(productOptionCategory)
                .description(this.description)
                .build();
    }
}