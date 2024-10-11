package io.ssafy.p.j11a307.store.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "TopCategory와 그에 해당하는 SubCategory 목록을 포함하는 DTO")
public record TopCategoryWithSubCategoriesDTO(
        @Schema(description = "TopCategory ID", example = "1")
        Integer id,

        @Schema(description = "TopCategory 이름", example = "한식")
        String name,

        @Schema(description = "해당 TopCategory에 속하는 SubCategory 목록")
        List<SubCategoryDTO> subCategories
) {}