package io.ssafy.p.j11a307.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "유저 fcm token 응답")
@Builder
public record UserFcmTokenResponse(
        @Schema(description = "유저 fcm token", example = "dkf83l2kd002-skdf2...")
        String fcmToken
) {}
