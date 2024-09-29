package io.ssafy.p.j11a307.product.dto;

import io.ssafy.p.j11a307.product.entity.Product;
import io.ssafy.p.j11a307.product.entity.ProductCategory;
import io.ssafy.p.j11a307.product.entity.ProductOptionCategory;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "상품 정보를 반환하기 위한 DTO")
public record ReadProductDTO(
        @Schema(description = "상품 ID", example = "1")
        Integer id,

        @Schema(description = "가게 ID", example = "101")
        Integer storeId,

        @Schema(description = "상품명", example = "Apple iPhone 14")
        String name,

        @Schema(description = "가격", example = "999")
        Integer price,

        @Schema(description = "이미지 경로", example = "/images/iphone14.jpg")
        String src,

        @Schema(description = "카테고리 목록")
        List<String> categories,

        @Schema(description = "옵션 카테고리 목록")
        List<String> optionCategories
) {
    // Product 엔티티를 받아서 DTO로 변환하는 생성자
    public ReadProductDTO(Product product) {
        this(
                product.getId(),
                product.getStoreId(),
                product.getName(),
                product.getPrice(),
                product.getSrc(),
                // ProductCategory 리스트에서 이름만 추출하여 리스트로 변환
                product.getCategories() != null ?
                        product.getCategories().stream()
                                .map(ProductCategory::getName)
                                .collect(Collectors.toList()) :
                        null,
                // ProductOptionCategory 리스트에서 이름만 추출하여 리스트로 변환
                product.getOptionCategories() != null ?
                        product.getOptionCategories().stream()
                                .map(ProductOptionCategory::getName)
                                .collect(Collectors.toList()) :
                        null
        );
    }
}
