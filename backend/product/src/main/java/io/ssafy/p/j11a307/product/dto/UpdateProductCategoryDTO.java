package io.ssafy.p.j11a307.product.dto;

import io.ssafy.p.j11a307.product.entity.ProductCategory;
import io.ssafy.p.j11a307.product.exception.BusinessException;
import io.ssafy.p.j11a307.product.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "상품 카테고리를 수정하기 위한 DTO")
public record UpdateProductCategoryDTO(
        @Schema(description = "카테고리명", example = "분식류")
        String name
//        @Schema(description = "상위 카테고리 ID", example = "3")
//        Integer parentCategoryId
) {
    // DTO를 사용해 엔티티를 업데이트하는 메서드
    public void updateEntity(ProductCategory category) {
        category.changeName(this.name); // 이름 변경

        // 부모 카테고리가 null이 아닐 때만 설정
//        if (this.parentCategoryId != null) {
//            category.changeParentCategory(new ProductCategory(this.parentCategoryId)); // ID로 부모 카테고리 설정
//        } else {
//            category.changeParentCategory(null); // 부모 카테고리를 null로 설정
//        }
    }
    // ProductCategory 엔티티를 받아서 DTO로 변환하는 생성자
    public UpdateProductCategoryDTO(ProductCategory productCategory) {
        this(
                productCategory.getName()
//                productCategory.getParentCategory() != null ? productCategory.getParentCategory().getId() : null
        );
    }
}