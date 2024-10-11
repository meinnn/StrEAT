package io.ssafy.p.j11a307.product.dto;

import io.ssafy.p.j11a307.product.entity.ProductPhoto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "ProductPhoto 조회 DTO")
public record ReadProductPhotoDTO(
        @Schema(description = "ProductPhoto ID", example = "1")
        Integer id,

        @Schema(description = "Product ID", example = "1")
        Integer productId,

        @Schema(description = "사진 경로", example = "https://s3.amazonaws.com/example.jpg")
        @Getter
        String src,

        @Schema(description = "생성일자", example = "2024-09-30T12:34:56")
        LocalDateTime createdAt
) {
    // 엔티티에서 DTO로 변환하는 생성자
    public ReadProductPhotoDTO(ProductPhoto productPhoto) {
        this(
                productPhoto.getId(),
                productPhoto.getProduct().getId(),
                productPhoto.getSrc(),  // 이미지 경로 포함
                productPhoto.getCreatedAt()  // 생성일자 포함
        );
    }
}
