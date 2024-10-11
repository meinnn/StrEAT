package io.ssafy.p.j11a307.order.dto;

import io.ssafy.p.j11a307.order.global.OrderCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Schema(description = "나의 각 주문 내역 데이터를 위한 DTO")
public record GetMyOrderListDTO (
    @Schema(description = "주문 내역 id", example = "2")
    Integer ordersId,

    @Schema(description = "주문 날짜", example = "2024-09-30 12:34:56.000000")
    LocalDateTime ordersCreatedAt,

    @Schema(description = "주문 상태", example = "PROCESSING")
    OrderCode orderStatus,

    @Schema(description = "리뷰 작성 여부", example = "false")
    Boolean isReviewed,

    @Schema(description = "점포 id", example = "2")
    Integer storeId,

    @Schema(description = "점포 사진", example = "https://streat-bucket.s3.ap-northeast-2.amazonaws.com/317a4b5f-cdefault_img.jpg")
    String storePhoto,

    @Schema(description = "점포 이름", example = "민지네 카페")
    String storeName,

    @Schema(description = "상품 리스트")
    List<GetStoreOrderListProductsDTO> products
){ }
