package io.ssafy.p.j11a307.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;

@Builder
public record GetSalesTopPlace (
        @Schema(description = "간편위치 id", example = "1")
        Integer id,

        @Schema(description = "간편위치 사진")
        String src,

        @Schema(description = "간편위치 이름", example = "역삼역 앞")
        String nickname,

        @Schema(description = "주소", example = "경기도")
        String address,

        @Schema(description = "위도", example = "37.2938")
        Double latitude,

        @Schema(description = "경도", example = "127.97389")
        Double longitude,

        @Schema(description = "주문횟수", example = "127")
        Integer orderCount,

        @Schema(description = "순위", example = "1")
        Integer rank

        ){ }
