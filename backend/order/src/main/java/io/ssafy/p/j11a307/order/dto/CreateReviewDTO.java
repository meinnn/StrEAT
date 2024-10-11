package io.ssafy.p.j11a307.order.dto;

import io.ssafy.p.j11a307.order.entity.OrdersId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "리뷰 작성 정보를 담기 위한 DTO")
@Getter
@ToString
public class CreateReviewDTO {
      @Schema(description = "리뷰 점수", example = "3")
      private Integer score;

      @Schema(description = "리뷰 내용", example = "사장님이 맛있고 음식이 친절해요")
       private String content;

//        @Schema(description = "사진", example = "사진 목록")
//        MultipartFile[] images

    @Builder
    public CreateReviewDTO(Integer score, String content) {
        this.score = score;
        this.content = content;
    }
}
