package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Map;
import java.util.List;

@Builder
public record GetDailySalesDTO(
        @Schema(description = "주문 리스트")
        List<GetDailySalesListDTO> dailySalesList,

        @Schema(description = "해당 일자 총 결제 금액")
        Integer dailyTotalPayAmount
){ }
