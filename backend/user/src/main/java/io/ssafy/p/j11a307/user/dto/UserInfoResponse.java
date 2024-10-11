package io.ssafy.p.j11a307.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "유저 정보")
@Builder
public record UserInfoResponse(
        @Schema(description = "이름", example = "김유경")
        String name,
        @Schema(description = "프로필 사진 경로", example = "http://k.kakaocdn.net/../img_110x110.jpg")
        String profileImgSrc,
        @Schema(description = "주문 상태 알림 설정 정보", example = "true / false")
        Boolean orderStatusAlert,
        @Schema(description = "찜 가게 오픈 알림 설정 정보", example = "true / false")
        Boolean dibsStoreAlert
) {}
