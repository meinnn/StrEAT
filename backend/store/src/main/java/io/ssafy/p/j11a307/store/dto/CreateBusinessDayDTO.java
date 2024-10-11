package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.BusinessDay;
import io.ssafy.p.j11a307.store.entity.Store;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "영업일을 생성하기 위한 DTO")
public record CreateBusinessDayDTO(

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
    // BusinessDay 엔티티로 변환하는 메서드
    public BusinessDay toEntity(Store store) {
        return BusinessDay.builder()
                .store(store)
                .mondayStart(this.mondayStart)
                .mondayEnd(this.mondayEnd)
                .tuesdayStart(this.tuesdayStart)
                .tuesdayEnd(this.tuesdayEnd)
                .wednesdayStart(this.wednesdayStart)
                .wednesdayEnd(this.wednesdayEnd)
                .thursdayStart(this.thursdayStart)
                .thursdayEnd(this.thursdayEnd)
                .fridayStart(this.fridayStart)
                .fridayEnd(this.fridayEnd)
                .saturdayStart(this.saturdayStart)
                .saturdayEnd(this.saturdayEnd)
                .sundayStart(this.sundayStart)
                .sundayEnd(this.sundayEnd)
                .build();
    }
}