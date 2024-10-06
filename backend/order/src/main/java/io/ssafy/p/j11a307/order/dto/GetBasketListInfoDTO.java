package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@Schema(description = "장바구니 상세 내역 정보를 담기 위한 DTO")
public class GetBasketListInfoDTO {
    @Schema(description = "내역 아이디", example = "12")
    private Integer basketId;

    @Schema(description = "개수", example = "1")
    private Integer quantity;

    @Schema(description = "가격", example = "1000")
    private Integer price;

    @Schema(description = "상품 아이디", example = "1")
    private Integer productId;

    @Schema(description = "상품명", example = "후라이드 치킨")
    private String productName;

    @Schema(description = "상품 사진", example = "https://streat-bucket.s3.ap-northeast-2.amazonaws.com/c7f8c1d0-42023.02.26%20%EA%B9%80%EB%AF%BC%EC%A7%80%EB%8B%98%28%ED%95%98%EB%A1%9C%297823.jpg")
    private String productSrc;

    @Schema(description = "옵션 이름 목록", example = "[\"매운맛\", \"소\"]")
    List<String> OptionNameList;

    @Schema(description = "상품 품절 상태", example = "true")
    Boolean stockStatus;

}
