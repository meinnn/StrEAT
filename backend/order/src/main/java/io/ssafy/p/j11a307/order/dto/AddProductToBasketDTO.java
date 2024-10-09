package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "장바구니 상품 추가를 위한 DTO")
public class AddProductToBasketDTO {
    @Schema(description = "상품 개수", example = "3")
    private Integer quantity;

    @Schema(description = "옵션 id 리스트", example = "[1,5]")
    private List<Integer> productOptionIds;
}
