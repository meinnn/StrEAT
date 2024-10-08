package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "장바구니 세부 옵션 정보를 위한 DTO")
public class GetBasketOptionDetailDTO {
    @Schema(description = "옵션 id", example = "1")
    private Integer optionId;

    @Schema(description = "옵션 이름", example = "순한맛")
    private String optionName;

    @Schema(description = "옵션 가격", example = "2000")
    private Integer optionPrice;

    @Schema(description = "선택 여부", example = "true")
    private Boolean isSelected;

    @Schema(description = "옵션이 속한 카테고리 id", example = "1")
    private Integer optionCategoryId;

    @Schema(description = "옵션이 속한 카테고리명", example = "매운맛 정도")
    private String optionCategoryName;

    @Schema(description = "옵션이 속한 카테고리의 필수선택 여부", example = "true")
    private Boolean isEssentialCategory;

    @Schema(description = "옵션이 속한 카테고리의 최소 선택 개수", example = "1")
    private Integer minSelectCategory;

    @Schema(description = "옵션이 속한 카테고리의 최대 선택 개수", example = "3")
    private Integer maxSelectCategory;
}
