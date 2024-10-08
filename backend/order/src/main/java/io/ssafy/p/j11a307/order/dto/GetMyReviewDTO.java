package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetMyReviewDTO {
    @Schema(description = "리뷰 목록")
    List<GetMyReviewListDTO> getMyReviewList;

    @Schema(description = "총 페이지 개수", example = "4")
    Integer totalPageCount;

    @Schema(description = "총 데이터 개수", example = "4")
    Long totalDataCount;
}
