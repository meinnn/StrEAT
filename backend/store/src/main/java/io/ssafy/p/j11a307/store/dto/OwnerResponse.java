package io.ssafy.p.j11a307.store.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "가게 사장님 아이디를 담기 위한 DTO")
public record OwnerResponse(
        @Schema(description = "사장님의 고유 ID", example = "1")
        Long id
) {}