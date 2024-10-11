package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
public record GetOrderDetailProductListDTO (
        @Schema(description = "상품 이름", example = "새우버거")
        String productName,

        @Schema(description = "수량", example = "3")
        Integer quantity,

        @Schema(description = "옵션 포함 가격", example = "200000")
        Integer productPrice,

        @Schema(description = "상품 사진", example = "https://streat-bucket.s3.ap-northeast-2.amazonaws.com/c7f8c1d0-42023.02.26%20%EA%B9%80%EB%AF%BC%EC%A7%80%EB%8B%98%28%ED%95%98%EB%A1%9C%297823.jpg")
        String productSrc,

        @Schema(description = "옵션 리스트")
        List<GetOrderDetailProductOptionListDTO> optionList

        ) {
}
