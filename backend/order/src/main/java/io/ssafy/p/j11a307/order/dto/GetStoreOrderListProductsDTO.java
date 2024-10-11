package io.ssafy.p.j11a307.order.dto;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "상품 리스트 DTO")
public record GetStoreOrderListProductsDTO (
        @Schema(description = "상품 이름", example = "닭다리")
        String productName,

        @Schema(description = "주문 개수", example = "1")
        Integer orderProductCount
    )
{}
