package io.ssafy.p.j11a307.product.dto;

import io.ssafy.p.j11a307.product.entity.Product;
import io.ssafy.p.j11a307.product.entity.ProductOption;
import io.ssafy.p.j11a307.product.entity.ProductOptionCategory;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "상품 옵션을 업데이트하기 위한 DTO")
public record UpdateProductOptionDTO(
        @Schema(description = "상품 옵션 ID", example = "1")
        Integer productId,

        @Schema(description = "옵션 카테고리 ID", example = "1")
        Integer productOptionCategoryId,

        @Schema(description = "옵션 이름", example = "매운맛")
        String productOptionName,

        @Schema(description = "옵션 가격", example = "2000")
        Integer productOptionPrice
) {
    public void updateEntity(ProductOption productOption, Product product, ProductOptionCategory productOptionCategory) {
        productOption.changeProductOptionName(productOption);
        productOption.changeProductOptionPrice(productOption);
        productOption.changeOptionCategory(productOptionCategory);
    }
}