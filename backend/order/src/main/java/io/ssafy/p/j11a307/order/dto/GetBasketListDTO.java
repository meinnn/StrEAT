package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@Schema(description = "내 장바구니 리스트 정보를 담기 위한 DTO")
public class GetBasketListDTO {
    @Schema(description = "점포 아이디", example = "1")
    private Integer storeId;

    @Schema(description = "점포 이름", example = "유경이네 햄버거")
    private String storeName;

    @Schema(description = "점포 사진", example = "https://streat-bucket.s3.ap-northeast-2.amazonaws.com/c7f8c1d0-42023.02.26%20%EA%B9%80%EB%AF%BC%EC%A7%80%EB%8B%98%28%ED%95%98%EB%A1%9C%297823.jpg")
    private String storeSrc;

    @Schema(description = "주문 예정 리스트")
    List<GetBasketListInfoDTO> basketList;

    @Schema(description = "총 페이지 개수", example = "4")
    Integer totalPageCount;

    @Schema(description = "총 데이터 개수", example = "4")
    Long totalDataCount;
}
