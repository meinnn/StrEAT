package io.ssafy.p.j11a307.product.dto;

import io.ssafy.p.j11a307.product.entity.Product;
import io.ssafy.p.j11a307.product.exception.BusinessException;
import io.ssafy.p.j11a307.product.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "상품을 생성하기 위한 DTO")
public record CreateProductDTO(
        @Schema(description = "가게 ID", example = "1")
        Integer storeId,

        @Schema(description = "상품명", example = "타코야끼")
        String name,

        @Schema(description = "가격", example = "2800")
        Integer price,

        @Schema(description = "이미지 URL", example = "/images/laptop.png")
        String src
) {
    public CreateProductDTO {
        // 필수 필드 검증
        if (storeId == null) {
            throw new BusinessException(ErrorCode.STORE_NOT_FOUND);
        }
        if (name == null || name.isEmpty()) {
            throw new BusinessException(ErrorCode.PRODUCT_NAME_NULL);
        }
        if (price == null || price < 0) {
            throw new BusinessException(ErrorCode.INVALID_PRICE);
        }
        if (src == null || src.isEmpty()) {
            throw new BusinessException(ErrorCode.PRODUCT_SRC_NULL);
        }
    }

    // DTO에서 Product 엔티티로 변환하는 메서드
    public Product toEntity() {
        return Product.builder()
                .storeId(this.storeId)  // 가게 ID 설정
                .name(this.name)  // 상품명 설정
                .price(this.price)  // 가격 설정
                .src(this.src)  // 이미지 URL 설정
                .build();
    }
}
