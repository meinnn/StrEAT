package io.ssafy.p.j11a307.store.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "가게 정보를 수정하기 위한 DTO")
public record StoreUpdateRequest(
        @Schema(description = "가게 이름", example = "SSAFY Cafe")
        String name,

        @Schema(description = "가게 주소", example = "서울특별시 강남구 테헤란로 212")
        String address,

        @Schema(description = "가게의 위도 정보", example = "37.5010")
        String latitude,

        @Schema(description = "가게의 경도 정보", example = "127.0396")
        String longitude,

        @Schema(description = "가게 유형 (예: 음식점, 카페 등)", example = "카페")
        String type,

        @Schema(description = "사장님의 은행 계좌 번호", example = "123-456-78901234")
        String bankAccount,

        @Schema(description = "사장님이 사용하는 은행명", example = "국민은행")
        String bankName,

        @Schema(description = "사장님이 가게에 남기고 싶은 한 마디", example = "정성을 다하는 가게가 되겠습니다!")
        String ownerWord,

        @Schema(description = "가게의 운영 상태 (예: OPEN, CLOSED 등)", example = "OPEN")
        String status,

        @Schema(description = "사장님 ID", example = "1")
        Long ownerId
) {}