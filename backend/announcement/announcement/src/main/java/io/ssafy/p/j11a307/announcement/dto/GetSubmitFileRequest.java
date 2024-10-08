package io.ssafy.p.j11a307.announcement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record GetSubmitFileRequest (
//        @Schema(description = "사장 이름", example = "이주호")
//        String ownerName,
//
//        @Schema(description = "성별", example = "남")
//        String gender,
//
//        @Schema(description = "연령", example = "23")
//        Integer age,
//
//        @Schema(description = "생년월일", example = "990202")
//        String birth,
//
//        @Schema(description = "본인 주소", example = "경기도 용인시 기흥구 보정동")
//        String address,
//
//        @Schema(description = "집전화 번호", example = "031-274-3878")
//        String home_num,
//
//        @Schema(description = "휴대폰 번호", example = "010-2572-3878")
//        String phone_num,
//
//        @Schema(description = "이메일", example = "jeuslee99@naver.com")
//        String email,
//
//        @Schema(description = "sns", example = "rat_leeho")
//        String sns,
//
//        @Schema(description = "푸드트럭 이름", example = "민지네 푸드트럭")
//        String truckName,
//
//        @Schema(description = "사업자등록번호", example = "1687-8162736")
//        String businessNum,
        @Schema(description = "참여 행사 이름", example = "삼성전자 어린이날 행사")
        String eventName
) { }
