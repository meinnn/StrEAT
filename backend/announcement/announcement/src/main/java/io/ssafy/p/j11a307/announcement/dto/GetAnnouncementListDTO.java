package io.ssafy.p.j11a307.announcement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record GetAnnouncementListDTO(
        @Schema(description = "공고 아이디", example = "1")
        Integer id,

        @Schema(description = "공고글 제목", example = "[2024년 6월7일] 동대문 패션 봉제 페스타")
        String recruitPostTitle,

        @Schema(description = "행사 이름", example = "동대문 패션 봉제 페스타")
        String eventName,

        @Schema(description = "행사일", example = "2024년 6.7 ")
        String eventDays,

        @Schema(description = "행사 운영시간", example = "10:00~17:00")
        String eventTimes,

        @Schema(description = "행사 장소", example = "동대문구청 광장")
        String eventPlace,

        @Schema(description = "모집 대수", example = "2대")
        String recruitSize,

        @Schema(description = "지원 조건", example = "협회 정회원 및 준회원")
        String entryConditions,

        @Schema(description = "특이사항", example = "1)전기,수도 지원 가능 2)위생 철저 3)어린이들이 이용할 수 있는 음식 위주 ▷협회 입점 회비 : 정회원 1일 10만원, 준회원 1일 20만원 ")
        String specialNotes
)
{ }
