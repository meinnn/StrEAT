package io.ssafy.p.j11a307.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "상품 전체 정보를 조회하기 위한 DTO")
public record ReadProductAllDTO(

        @Schema(description = "상품 ID", example = "1")
        @NotNull(message = "상품 ID는 필수 입력 항목입니다.")
        Integer productId,

        @Schema(description = "상품명", example = "타코야끼")
        @NotNull(message = "상품명은 필수 입력 항목입니다.")
        String name,

        @Schema(description = "상품 설명", example = "맛있는 타코야끼")
        @NotNull(message = "상품 설명은 필수 입력 항목입니다.")
        String description,

        @Schema(description = "상품 가격", example = "5000")
        @NotNull(message = "상품 가격은 필수 입력 항목입니다.")
        Integer price,

        @Schema(description = "상품 카테고리 ID", example = "1")
        @NotNull(message = "상품 카테고리는 필수 입력 항목입니다.")
        Integer categoryId,

        @Schema(description = "재고 상태", example = "true")
        Boolean stockStatus,

        @Schema(description = "상품 사진 목록")
        List<String> photos,  // 상품 사진의 src 경로 리스트

        @Schema(description = "상품 옵션 카테고리 목록 (예: 사이즈, 맛 선택)")
        List<ReadProductOptionCategoryDTO> optionCategories

) {}
