package io.ssafy.p.j11a307.product.dto;

import io.ssafy.p.j11a307.product.entity.ProductOptionCategory;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "옵션 카테고리를 업데이트하기 위한 DTO")
public record UpdateProductOptionCategoryDTO(

        @Schema(description = "옵션 카테고리명", example = "색상")
        String name,

        @Schema(description = "필수 여부", example = "true")
        Boolean isEssential,

        @Schema(description = "최대 선택 개수", example = "3")
        Integer maxSelect,

        @Schema(description = "최소 선택 개수", example = "1")
        Integer minSelect,

        @Schema(description = "상품 옵션 목록")
        List<UpdateProductOptionDTO> productOptions
) {
    public UpdateProductOptionCategoryDTO(ProductOptionCategory optionCategory) {
        this(
                optionCategory.getName(),
                optionCategory.getIsEssential(),
                optionCategory.getMaxSelect(),
                optionCategory.getMinSelect(),
                optionCategory.getOptions() != null
                        ? optionCategory.getOptions().stream()
                        .map(option -> new UpdateProductOptionDTO(
                                option.getId(), // 옵션 ID
                                option.getProductOptionCategory().getId(), // 옵션 카테고리 ID
                                option.getProductOptionName(), // 옵션 이름
                                option.getProductOptionPrice() // 옵션 가격
                        ))
                        .toList()
                        : List.of()  // 옵션 목록이 null일 경우 빈 리스트 반환
        );
    }
    // DTO에서 ProductOptionCategory 엔티티로 변환하거나 업데이트하는 메서드
    public void updateEntity(ProductOptionCategory optionCategory) {
        if (this.name != null) {
            optionCategory.changeName(this.name);
        }
        if (this.isEssential != null) {
            optionCategory.changeIsEssential(this.isEssential);
        }
        if (this.maxSelect != null) {
            optionCategory.changeMaxSelect(this.maxSelect);
        }
        if (this.minSelect != null) {
            optionCategory.changeMinSelect(this.minSelect);
        }

        // 상품 옵션 목록 업데이트 처리
        if (this.productOptions != null && !this.productOptions.isEmpty()) {
            // 기존의 옵션들을 업데이트할 로직을 구현합니다.
            this.productOptions.forEach(optionDTO -> {
                // 여기서 옵션 엔티티를 찾아서 업데이트 로직을 구현하세요.
                // 예를 들어, 기존 옵션을 가져온 후 DTO를 통해 업데이트할 수 있습니다.
                // optionDTO.updateEntity(existingProductOption);
            });
        }
    }
}