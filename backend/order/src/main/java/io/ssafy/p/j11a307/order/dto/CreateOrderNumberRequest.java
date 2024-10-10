package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateOrderNumberRequest (
        @Schema(description = "총 가격", example = "30000")
        Integer totalPrice,

        @Schema(description = "상품 리스트")
        List<CreateOrderNumberProductListDTO> orderProducts
){ }
