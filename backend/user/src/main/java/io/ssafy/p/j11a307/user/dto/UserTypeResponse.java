package io.ssafy.p.j11a307.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "로그인한 유저의 타입 정보")
@Builder
public record UserTypeResponse(
        @Schema(description = "유저 타입", example = "CUSTOMER, OWNER, NOT_SELECTED")
        String userType
) {}
