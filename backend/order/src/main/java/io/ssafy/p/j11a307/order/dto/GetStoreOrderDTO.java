package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@Schema(description = "주문 내역 리스트 DTO")
public class GetStoreOrderDTO {
    @Schema(description = "주문 목록")
    List<GetStoreOrderListDTO> getStoreOrderLists;

    @Schema(description = "총 페이지 개수", example = "4")
    Integer totalPageCount;

    @Schema(description = "총 데이터 개수", example = "4")
    Long totalDataCount;


}
