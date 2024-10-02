package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.StorePhoto;
import io.swagger.v3.oas.annotations.media.Schema;

public class ReadStorePhotoSrcDTO {
    @Schema(description = "사진 경로", example = "https://s3.amazonaws.com/example.jpg")
    private String src;

    public ReadStorePhotoSrcDTO(StorePhoto storePhoto) {
        this.src = storePhoto.getSrc();  // 필요한 필드만 가져오기
    }

    // Getter
    public String getSrc() {
        return src;
    }
}
