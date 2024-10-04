package io.ssafy.p.j11a307.product.dto;

import io.ssafy.p.j11a307.product.entity.ProductOption;

public record ReadProductOptionDTO(
        Integer id,                      // ProductOption ID
        Integer productId,                // Product ID
        Integer productOptionCategoryId,  // 옵션 카테고리 ID
        String productOptionName, //옵션 이름
        Integer productOptionPrice // 옵션 가격
) {
    // ProductOption 엔티티에서 DTO로 변환하는 생성자
    public ReadProductOptionDTO(ProductOption productOption) {
        this(
                productOption.getId(),
                productOption.getProduct().getId(),
                productOption.getProductOptionCategory().getId(),
                productOption.getProductOptionName(),
                productOption.getProductOptionPrice()
        );
    }
}