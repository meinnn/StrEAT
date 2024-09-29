package io.ssafy.p.j11a307.product.dto;

import io.ssafy.p.j11a307.product.entity.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "상품 카테고리를 수정하기 위한 DTO")
public record UpdateProductCategoryDTO(
        @Schema(description = "카테고리명", example = "분식류")
        String name,
        @Schema(description = "상위 카테고리 ID", example = "3")
        Integer parentCategoryId
) {
    // DTO를 사용해 엔티티를 업데이트하는 메서드
    public void updateEntity(ProductCategory productCategory) {
        if (this.name != null && !this.name.isEmpty()) {
            productCategory.changeName(this.name);  // 카테고리명 변경
        }
        if (this.parentCategoryId != null) {
            // 여기서 parentCategory는 엔티티 매니저 등을 통해 설정할 필요가 있음
            // productCategory.setParentCategory(parentCategory); (parentCategory를 설정하는 로직 필요)
        }
    }

    // ProductCategory 엔티티를 받아서 DTO로 변환하는 생성자
    public UpdateProductCategoryDTO(ProductCategory productCategory) {
        this(
                productCategory.getName(),
                productCategory.getParentCategory() != null ? productCategory.getParentCategory().getId() : null
        );
    }
}