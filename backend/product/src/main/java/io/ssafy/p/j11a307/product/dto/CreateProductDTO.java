package io.ssafy.p.j11a307.product.dto;

import io.ssafy.p.j11a307.product.entity.Product;
import io.ssafy.p.j11a307.product.entity.ProductCategory;
import io.ssafy.p.j11a307.product.exception.BusinessException;
import io.ssafy.p.j11a307.product.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "상품을 생성하기 위한 DTO")
public record CreateProductDTO(

        @Schema(description = "상품명", example = "타코야끼")
        String name,

        @Schema(description = "가격", example = "2800")
        Integer price,

        @Schema(description = "상품설명", example = "통통한 문어가 들어있는 타코야끼")
        String description,

        @Schema(description = "카테고리 ID", example = "1")
        Integer categoryId
) {
    // 생성자에서 유효성 검사
    public CreateProductDTO {
        if (name == null || name.isEmpty()) {
            throw new BusinessException(ErrorCode.PRODUCT_NAME_NULL);
        }
        if (price == null || price < 0) {
            throw new BusinessException(ErrorCode.INVALID_PRICE);
        }
        if (description == null || description.isEmpty()) {
            throw new BusinessException(ErrorCode.PRODUCT_DESCRIPTION_NULL);
        }
        if (categoryId == null || categoryId <= 0) {
            throw new BusinessException(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND);
        }
    }

    // DTO에서 Product 엔티티로 변환하는 메서드
    public Product toEntity(ProductCategory category) {
        return Product.builder()
                .name(this.name)
                .price(this.price)
                .description(this.description)
                .category(category)  // 카테고리 설정
                .build();
    }
}