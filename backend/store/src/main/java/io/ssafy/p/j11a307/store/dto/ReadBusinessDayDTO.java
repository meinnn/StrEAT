package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.BusinessDay;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "영업일 정보를 반환하기 위한 DTO")
public record ReadBusinessDayDTO(
        @Schema(description = "영업일 ID", example = "1")
        Integer id,

        @Schema(description = "가게 ID", example = "1")
        Integer storeId,

        @Schema(description = "월요일 시작 시간", example = "09:00")
        String mondayStart,

        @Schema(description = "월요일 종료 시간", example = "18:00")
        String mondayEnd,

        @Schema(description = "화요일 시작 시간", example = "09:00")
        String tuesdayStart,

        @Schema(description = "화요일 종료 시간", example = "18:00")
        String tuesdayEnd,

        @Schema(description = "수요일 시작 시간", example = "09:00")
        String wednesdayStart,

        @Schema(description = "수요일 종료 시간", example = "18:00")
        String wednesdayEnd,

        @Schema(description = "목요일 시작 시간", example = "09:00")
        String thursdayStart,

        @Schema(description = "목요일 종료 시간", example = "18:00")
        String thursdayEnd,

        @Schema(description = "금요일 시작 시간", example = "09:00")
        String fridayStart,

        @Schema(description = "금요일 종료 시간", example = "18:00")
        String fridayEnd,

        @Schema(description = "토요일 시작 시간", example = "09:00")
        String saturdayStart,

        @Schema(description = "토요일 종료 시간", example = "18:00")
        String saturdayEnd,

        @Schema(description = "일요일 시작 시간", example = "09:00")
        String sundayStart,

        @Schema(description = "일요일 종료 시간", example = "18:00")
        String sundayEnd
) {
    // BusinessDay 엔티티를 받아서 DTO로 변환하는 생성자
    public ReadBusinessDayDTO(BusinessDay businessDay) {
        this(
                businessDay.getId(),
                businessDay.getStore().getId(),
                businessDay.getMondayStart(),
                businessDay.getMondayEnd(),
                businessDay.getTuesdayStart(),
                businessDay.getTuesdayEnd(),
                businessDay.getWednesdayStart(),
                businessDay.getWednesdayEnd(),
                businessDay.getThursdayStart(),
                businessDay.getThursdayEnd(),
                businessDay.getFridayStart(),
                businessDay.getFridayEnd(),
                businessDay.getSaturdayStart(),
                businessDay.getSaturdayEnd(),
                businessDay.getSundayStart(),
                businessDay.getSundayEnd()
        );
    }
}