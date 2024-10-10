package io.ssafy.p.j11a307.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record GetStoreSimpleLocationDTO(
        @Schema(description = "가게위치저장이름", example = "멀티캠퍼스 앞")
        String nickname,

        @Schema(description = "가게주소", example = "서울특별시 강남구 테헤란로 212")
        String address,

        @Schema(description = "위도", example = "37.5665")
        @NotNull(message = "위도는 필수 입력 항목입니다.")
        Double latitude,

        @Schema(description = "경도", example = "126.9780")
        @NotNull(message = "경도는 필수 입력 항목입니다.")
        Double longitude,

        @Schema(description = "이미지 경로")
        String src
) {
}
