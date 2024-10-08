package io.ssafy.p.j11a307.order.dto;

import io.ssafy.p.j11a307.order.global.OrderCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record GetMyOngoingOrderDTO (
        @Schema(description = "주문 리스트")
        List<GetMyOngoingOrderDetailDTO> orderList
) { }
