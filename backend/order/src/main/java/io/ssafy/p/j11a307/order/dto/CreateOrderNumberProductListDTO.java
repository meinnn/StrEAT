package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateOrderNumberProductListDTO (
        @Schema(description = "상품 아이디", example = "1")
        Integer id,

        @Schema(description = "상품 개수", example = "3")
        Integer quantity,

        @Schema(description = "옵션 리스트", example = "[1,5]")
        List<Integer> orderProductOptions
){

}
