package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Schema(description = "점포별 리뷰 조회 정보를 담기 위한 DTO")
public class GetStoreReviewListDTO {
    @Schema(description = "유저 이름", example = "이주호")
    String userName;

    @Schema(description = "유저 프로필 사진")
    String userPhoto;

    @Schema(description = "주문상품 리스트", example = "새우버거, 불고기버거")
    List<String> orderProducts;

    @Schema(description = "별점", example = "3")
    Integer score;

    @Schema(description = "리뷰 상세내용", example = "사장님이 맛있고 음식이 친절해요")
    String content;

    @Schema(description = "리뷰 작성일자")
    LocalDateTime createdAt;

    @Schema(description = "리뷰 사진 리스트")
    List<String> srcList;

    @Builder
    public GetStoreReviewListDTO(String userName, String userPhoto, List<String> orderProducts, Integer score, String content, LocalDateTime createdAt, List<String> srcList) {
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.orderProducts = orderProducts;
        this.score = score;
        this.content = content;
        this.createdAt = createdAt;
        this.srcList = srcList;
    }
}
