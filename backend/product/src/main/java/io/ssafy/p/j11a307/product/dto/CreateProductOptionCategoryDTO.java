package io.ssafy.p.j11a307.product.dto;

import io.ssafy.p.j11a307.product.entity.Product;
import io.ssafy.p.j11a307.product.entity.ProductOptionCategory;
import io.ssafy.p.j11a307.product.exception.BusinessException;
import io.ssafy.p.j11a307.product.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "옵션 카테고리를 생성하기 위한 DTO")
public record CreateProductOptionCategoryDTO(
        @Schema(description = "상품 ID", example = "1")
        Integer productId,

        @Schema(description = "옵션 카테고리명", example = "색상")
        String name,

        @Schema(description = "상위 옵션 카테고리 ID", example = "0")
        Integer parentOptionCategoryId,

        @Schema(description = "필수 여부", example = "true")
        Boolean isEssential,

        @Schema(description = "최대 선택 개수", example = "3")
        Integer maxSelect
) {
        public CreateProductOptionCategoryDTO {
                // 필수 필드 검증
                if (productId == null) {
                        throw new BusinessException(ErrorCode.PRODUCT_ID_NULL);
                }
                if (name == null || name.isEmpty()) {
                        throw new BusinessException(ErrorCode.PRODUCT_OPTION_CATEGORY_NAME_NULL);
                }
                if (maxSelect == null || maxSelect < 0) {
                        throw new BusinessException(ErrorCode.INVALID_MAX_SELECT);
                }
        }

        // DTO에서 ProductOptionCategory 엔티티로 변환하는 메서드
        public ProductOptionCategory toEntity(Product product, ProductOptionCategory parentCategory) {
                return ProductOptionCategory.builder()
                        .product(product)  // 연관된 Product 설정
                        .name(this.name)  // 옵션 카테고리명 설정
                        .isEssential(this.isEssential)  // 필수 여부 설정
                        .maxSelect(this.maxSelect)  // 최대 선택 개수 설정
                        .parentCategory(parentCategory)  // 부모 옵션 카테고리 설정
                        .build();
        }
}
