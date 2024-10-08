package io.ssafy.p.j11a307.product.dto;

import io.ssafy.p.j11a307.product.entity.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "상품 카테고리 정보를 반환하기 위한 DTO")
public record ReadProductCategoryDTO(
        @Schema(description = "카테고리 ID", example = "1")
        Integer id,

        @Schema(description = "카테고리명", example = "분식류")
        String name

//        @Schema(description = "상위 카테고리 ID", example = "1")
//        Integer parentCategoryId
) {
    // ProductCategory 엔티티를 받아서 DTO로 변환하는 생성자
    public ReadProductCategoryDTO(ProductCategory productCategory) {
        this(
                productCategory.getId(),
                productCategory.getName()
//                productCategory.getParentCategory() != null ? productCategory.getParentCategory().getId() : null
        );
    }
}