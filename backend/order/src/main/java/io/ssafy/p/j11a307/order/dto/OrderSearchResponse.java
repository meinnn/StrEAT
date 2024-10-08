package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "검색 응답 정보")
@Builder
public record OrderSearchResponse (
    @Schema(description = "검색된 주문 목록", example = "4")
    List<GetOrderDetailDTO> searchOrderList,

    @Schema(description = "검색된 주문 개수", example = "5")
    Integer totalCount,

    @Schema(description = "총 페이지 개수", example = "4")
    Integer totalPageCount,

    @Schema(description = "총 데이터 개수", example = "4")
    Long totalDataCount
)
{ }
