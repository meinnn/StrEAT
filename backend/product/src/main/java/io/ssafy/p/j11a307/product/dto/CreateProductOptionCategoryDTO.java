package io.ssafy.p.j11a307.product.dto;

import io.ssafy.p.j11a307.product.entity.Product;
import io.ssafy.p.j11a307.product.entity.ProductOptionCategory;
import io.ssafy.p.j11a307.product.exception.BusinessException;
import io.ssafy.p.j11a307.product.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "옵션 카테고리를 생성하기 위한 DTO")
public record CreateProductOptionCategoryDTO(
        @Schema(description = "상품 ID", example = "1")
        Integer productId,

        @Schema(description = "옵션 카테고리명", example = "맛고르기")
        String name,

        @Schema(description = "필수 여부", example = "true")
        Boolean isEssential,

        @Schema(description = "최대 선택 개수", example = "3")
        Integer maxSelect,

        @Schema(description = "최소 선택 개수", example = "1")
        Integer minSelect,

        @Schema(description = "상품 옵션 목록")
        List<CreateProductOptionDTO> productOptions
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
                if (minSelect == null || minSelect < 0) {
                        throw new BusinessException(ErrorCode.INVALID_MIN_SELECT);
                }

                if (productOptions == null || productOptions.isEmpty()) {
                        throw new BusinessException(ErrorCode.PRODUCT_OPTION_EMPTY);
                }
                for (CreateProductOptionDTO option : productOptions) {
                        if (option.productOptionName() == null || option.productOptionName().isEmpty()) {
                                throw new BusinessException(ErrorCode.PRODUCT_OPTION_NAME_NULL);
                        }
                        if (option.productOptionPrice() == null || option.productOptionPrice() < 0) {
                                throw new BusinessException(ErrorCode.INVALID_PRICE);
                        }
                }

        }

        public ProductOptionCategory toEntity(Product product) {
                return ProductOptionCategory.builder()
                        .product(product)  // 연관된 Product 설정
                        .name(this.name)   // 옵션 카테고리명 설정
                        .isEssential(this.isEssential)  // 필수 여부 설정
                        .maxSelect(this.maxSelect)  // 최대 선택 개수 설정
                        .minSelect(this.minSelect)  // 최소 선택 개수 설정
                        .build();
        }
}
