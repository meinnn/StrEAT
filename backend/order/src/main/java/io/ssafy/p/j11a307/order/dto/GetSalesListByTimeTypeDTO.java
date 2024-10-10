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

        @Schema(description = "오늘 상품 매출")
        Map<String, GetSalesListByTimeTypeProductDTO> dailyProduct,

        @Schema(description = "오늘 총 판매수")
        Integer dailyTotal,

        @Schema(description = "주별 매출")
        Map<YearWeek, Integer> weekly,

        @Schema(description = "이번주 상품 매출")
        Map<String, GetSalesListByTimeTypeProductDTO> weeklyProduct,

        @Schema(description = "이번주 총 판매수")
        Integer weeklyTotal,

        @Schema(description = "월별 매출")
        Map<YearMonth, Integer> monthly,

        @Schema(description = "이번달 상품 매출")
        Map<String, GetSalesListByTimeTypeProductDTO> monthlyProduct,

        @Schema(description = "이번달 총 판매수")
        Integer monthlyTotal,

        @Schema(description = "연별 매출")
        Map<Year, Integer> yearly,

        @Schema(description = "올해 상품 매출")
        Map<String, GetSalesListByTimeTypeProductDTO> yearlyProduct,

        @Schema(description = "올해 총 판매수")
         Integer yearlyTotal
) { }
