package io.ssafy.p.j11a307.store.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "SubCategory 정보를 포함하는 DTO")
public record SubCategoryDTO(
        @Schema(description = "SubCategory ID", example = "1")
        Integer id,

        @Schema(description = "SubCategory 이름", example = "토스트/샌드위치/샐러드")
        String name
) {}