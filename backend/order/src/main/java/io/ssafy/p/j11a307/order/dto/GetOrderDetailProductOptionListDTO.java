package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;


public record GetOrderDetailProductOptionListDTO (
        @Schema(description = "옵션 이름", example = "순한맛")
        String optionName,

        @Schema(description = "옵션 가격", example = "2000")
        Integer optionPrice
){ }
