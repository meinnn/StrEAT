package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.StoreLocationPhoto;
import io.swagger.v3.oas.annotations.media.Schema;

public class ReadStoreLocationPhotoSrcDTO {
    @Schema(description = "이미지 경로", example = "https://s3.amazonaws.com/example.jpg")
    private String src;

    public ReadStoreLocationPhotoSrcDTO(StoreLocationPhoto storeLocationPhoto) {
        this.src = storeLocationPhoto.getSrc();  // 필요한 필드만 가져오기
    }

    // Getter
    public String getSrc() {
        return src;
    }
}
