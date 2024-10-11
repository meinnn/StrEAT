package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record GetStoreWaitingDTO (
        @Schema(description = "대기중인 팀", example = "3")
        Integer waitingTeam,

        @Schema(description = "대기중인 메뉴", example = "6")
        Integer waitingMenu
) { }
