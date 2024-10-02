package io.ssafy.p.j11a307.product.dto;

import io.ssafy.p.j11a307.product.entity.Product;
import io.ssafy.p.j11a307.product.entity.ProductCategory;
import io.ssafy.p.j11a307.product.exception.BusinessException;
import io.ssafy.p.j11a307.product.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "상품 카테고리를 생성하기 위한 DTO")
public record CreateProductCategoryDTO(
        @Schema(description = "카테고리명", example = "분식")
        String name,

        @Schema(description = "상위 카테고리 ID", example = "0")
        Integer parentCategoryId
) {
    public CreateProductCategoryDTO {
        if (name == null || name.isEmpty()) {
            throw new BusinessException(ErrorCode.PRODUCT_CATEGORY_NAME_NULL);
        }
    }

    // DTO에서 ProductCategory 엔티티로 변환하는 메서드
    public ProductCategory toEntity(Product product, ProductCategory parentCategory) {
        return ProductCategory.builder()
                .product(product)  // 연관된 Product 설정
                .name(this.name)  // 카테고리명 설정
                .parentCategory(parentCategory) // 부모 카테고리 설정 (null 가능)
                .build();
    }
}
