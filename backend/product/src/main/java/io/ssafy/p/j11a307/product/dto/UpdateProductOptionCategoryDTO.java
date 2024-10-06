package io.ssafy.p.j11a307.product.dto;

import io.ssafy.p.j11a307.product.entity.ProductOptionCategory;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "옵션 카테고리를 업데이트하기 위한 DTO")
public record UpdateProductOptionCategoryDTO(

        @Schema(description = "옵션 카테고리명", example = "색상")
        String name,

        @Schema(description = "필수 여부", example = "true")
        Boolean isEssential,

        @Schema(description = "최대 선택 개수", example = "3")
        Integer maxSelect,

        @Schema(description = "최소 선택 개수", example = "1")
        Integer minSelect

//        @Schema(description = "상위 옵션 카테고리 ID", example = "1")
//        Integer parentOptionCategoryId
) {
    // ProductOptionCategory 엔티티를 받아서 DTO로 변환하는 생성자
    public UpdateProductOptionCategoryDTO(ProductOptionCategory optionCategory) {
        this(
                optionCategory.getName(),
                optionCategory.getIsEssential(),
                optionCategory.getMaxSelect(),
                optionCategory.getMinSelect()
//                optionCategory.getParentCategory() != null ? optionCategory.getParentCategory().getId() : null
        );
    }

    // DTO에서 ProductOptionCategory 엔티티로 변환하는 메서드
    public void updateEntity(ProductOptionCategory optionCategory) {
        optionCategory.changeName(this.name);
        optionCategory.changeIsEssential(this.isEssential);
        optionCategory.changeMaxSelect(this.maxSelect);
        optionCategory.changeMinSelect(this.minSelect);
//        optionCategory.changeParentCategory(parentCategory); // 상위 카테고리 변경
    }
}