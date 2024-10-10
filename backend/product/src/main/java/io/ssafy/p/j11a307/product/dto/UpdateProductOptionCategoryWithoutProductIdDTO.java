package io.ssafy.p.j11a307.product.dto;

import io.ssafy.p.j11a307.product.entity.Product;
import io.ssafy.p.j11a307.product.entity.ProductOptionCategory;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "상품 ID 없이 옵션 카테고리를 업데이트하기 위한 DTO")
public record UpdateProductOptionCategoryWithoutProductIdDTO(

        @Schema(description = "옵션 카테고리명", example = "맛고르기")
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
    // null 값을 빈 리스트로 초기화하는 메서드 추가
    public List<UpdateProductOptionDTO> getProductOptions() {
        return productOptions == null ? List.of() : productOptions;
    }

    // 기존의 옵션 카테고리를 업데이트할 때 사용될 메서드
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