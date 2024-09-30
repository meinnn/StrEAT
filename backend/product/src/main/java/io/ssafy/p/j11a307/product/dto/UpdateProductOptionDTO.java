package io.ssafy.p.j11a307.product.dto;

import io.ssafy.p.j11a307.product.entity.Product;
import io.ssafy.p.j11a307.product.entity.ProductOption;
import io.ssafy.p.j11a307.product.entity.ProductOptionCategory;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "상품 옵션을 업데이트하기 위한 DTO")
public record UpdateProductOptionDTO(
        Integer productId,
        @Schema(description = "옵션 카테고리 ID", example = "1")
        Integer productOptionCategoryId,

        @Schema(description = "옵션 설명", example = "사이즈 옵션")
        String description
) {
    public void updateEntity(ProductOption productOption, Product product, ProductOptionCategory productOptionCategory) {
        productOption.changeDescription(this.description);
        productOption.changeOptionCategory(productOptionCategory);
    }
}