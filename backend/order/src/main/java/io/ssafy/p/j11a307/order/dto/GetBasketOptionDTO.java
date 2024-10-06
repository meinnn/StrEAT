package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@Schema(description = "장바구니 세부 정보를 위한 DTO")
public class GetBasketOptionDTO {
    @Schema(description = "내역 아이디", example = "12")
    private Integer basketId;

    @Schema(description = "상품 이름", example = "새우버거")
    private String productName;

    @Schema(description = "상품 가격", example = "200000")
    private Integer productPrice;

    @Schema(description = "수량", example = "3")
    private Integer quantity;

    @Schema(description = "재고 상태", example = "true")
    Boolean stockStatus;

    @Schema(description = "옵션 상세 리스트")
    Map<Integer, List<GetBasketOptionDetailDTO>> getBasketOptionDetailMap;
    //List<GetBasketOptionDetailDTO> getBasketOptionDetailDTOs;

}
