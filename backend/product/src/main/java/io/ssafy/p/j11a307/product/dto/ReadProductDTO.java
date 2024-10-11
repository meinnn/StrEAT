package io.ssafy.p.j11a307.product.dto;
import io.ssafy.p.j11a307.product.entity.ProductPhoto;
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

        @Schema(description = "가게 ID", example = "1")
        Integer storeId,

        @Schema(description = "상품명", example = "떡볶이")
        String name,

        @Schema(description = "가격", example = "2800")
        Integer price,

        @Schema(description = "상품설명", example = "통통한 문어가 들어있는 타코야끼")
        String description,

        @Schema(description = "재고 상태", example = "true")
        Boolean stockStatus,

        @Schema(description = "카테고리 ID")
        Integer category,

        @Schema(description = "옵션 카테고리 목록")
        List<Integer> optionCategories,

        @Schema(description = "상품 사진 목록")
        List<String> photos  // 상품 사진의 src 경로 리스트
) {
    // Product 엔티티를 받아서 DTO로 변환하는 생성자
    public ReadProductDTO(Product product) {
        this(
                product.getId(),
                product.getStoreId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getStockStatus(),
                product.getCategory() != null ? product.getCategory().getId() : null,   // null일 경우 빈 리스트 반환
                product.getOptionCategories() != null ?
                        product.getOptionCategories().stream()
                                .map(ProductOptionCategory::getId) // ID로 변경
                                .collect(Collectors.toList()) :
                        List.of(),  // null일 경우 빈 리스트 반환
                product.getPhotos() != null ?
                        product.getPhotos().stream()
                                .map(ProductPhoto::getSrc) // 이미지 경로만 추출
                                .collect(Collectors.toList()) :
                        List.of() // null일 경우 빈 리스트 반환
        );
    }
}