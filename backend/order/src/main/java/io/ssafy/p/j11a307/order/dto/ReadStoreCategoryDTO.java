package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Store의 SubCategory와 TopCategory 정보를 포함한 DTO")
public record ReadStoreCategoryDTO(
        @Schema(description = "SubCategory ID", example = "1")
        Integer subCategoryId,

        @Schema(description = "SubCategory 이름", example = "김밥/만두/분식")
        String subCategoryName,

        @Schema(description = "SubCategory 코드", example = "I20701")
        String subCode,

        @Schema(description = "TopCategory ID", example = "1")
        Integer topCategoryId,

        @Schema(description = "TopCategory 이름", example = "한식")
        String topCategoryName,

        @Schema(description = "TopCategory 코드", example = "I210")
        String topCode
) {}
