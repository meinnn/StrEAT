package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "유저 정보")
@Builder
public record UserInfoResponse(
        @Schema(description = "이름", example = "김유경")
        String name,
        @Schema(description = "프로필 사진 경로", example = "http://k.kakaocdn.net/../img_110x110.jpg")
        String profileImgSrc
) {}
