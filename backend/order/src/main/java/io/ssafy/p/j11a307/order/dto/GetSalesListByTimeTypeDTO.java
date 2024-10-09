package io.ssafy.p.j11a307.order.dto;

import io.ssafy.p.j11a307.order.global.YearWeek;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@Builder
public record GetSalesListByTimeTypeDTO(
        @Schema(description = "일별 매출")
        Map<LocalDate, Integer> daily,

        @Schema(description = "주별 매출")
        Map<YearWeek, Integer> weekly,

        @Schema(description = "월별 매출")
        Map<YearMonth, Integer> monthly,

        @Schema(description = "연별 매출")
        Map<Year, Integer> yearly
) { }
