package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(description = "내 주문 내역 리스트 DTO")
public record GetMyOrderDTO (
        @Schema(description = "주문 내역 목록")
        List<GetMyOrderListDTO> getMyOrderList,

        @Schema(description = "총 페이지 개수", example = "4")
        Integer totalPageCount,

        @Schema(description = "총 데이터 개수", example = "4")
        Long totalDataCount

) {}
