package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Schema(description = "옵션 카테고리를 조회하기 위한 DTO")
public record ReadProductOptionCategoryDTO(
        Integer id,
        Integer productId,
        String name,
        Boolean isEssential,
        Integer minSelect,
        Integer maxSelect,
        //Integer parentOptionCategoryId,
        //List<ReadProductOptionCategoryDTO> subCategories,
        List<ReadProductOptionDTO> options
) {}
