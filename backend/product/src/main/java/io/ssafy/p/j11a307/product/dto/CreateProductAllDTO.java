package io.ssafy.p.j11a307.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "상품 전체 정보를 생성하기 위한 DTO")
public record CreateProductAllDTO(

@Schema(description = "상품명", example = "타코야끼")
@NotNull(message = "상품명은 필수 입력 항목입니다.")
String name,

@Schema(description = "상품 설명", example = "맛있는 타코야끼")
@NotNull(message = "상품 설명은 필수 입력 항목입니다.")
String description,

@Schema(description = "상품 가격", example = "5000")
@NotNull(message = "상품 가격은 필수 입력 항목입니다.")
Integer price,

@Schema(description = "상품 카테고리 목록 (예: 한식, 중식)")
@NotNull(message = "상품 카테고리는 필수 입력 항목입니다.")
Integer categoryId,

@Schema(description = "상품 옵션 카테고리 목록 (예: 사이즈, 맛 선택)")
List<CreateProductOptionCategoryWithoutProductIdDTO> optionCategories
)
{
    public CreateProductDTO toCreateProductDTO() {
        return new CreateProductDTO(name, price, description, categoryId);
    }
}