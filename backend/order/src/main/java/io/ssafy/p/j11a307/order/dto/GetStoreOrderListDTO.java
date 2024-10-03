package io.ssafy.p.j11a307.order.dto;

import io.ssafy.p.j11a307.order.global.OrderCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Schema(description = "각 주문 내역 데이터를 위한 DTO")
public class GetStoreOrderListDTO {
    @Schema(description = "주문내역 id", example = "1")
    private Integer id;

    @Schema(description = "주문번호", example = "A237482374")
    private String orderNumber;

    @Schema(description = "주문 들어온 시각", example = "2024-09-30 12:34:56.000000")
    private LocalDateTime orderCreatedAt;

    @Schema(description = "주문 상태", example = "PROCESSING")
    private OrderCode status;

    @Schema(description = "메뉴 개수", example = "3")
    private Integer menuCount;

    @Schema(description = "총 가격", example = "200000")
    private Integer totalPrice;

    @Schema(description = "상품 리스트")
    List<GetStoreOrderListProductsDTO> products;

}
