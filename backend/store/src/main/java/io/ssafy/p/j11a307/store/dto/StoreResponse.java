package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.Store;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "가게 정보를 담기 위한 DTO")
public record StoreResponse(
        @Schema(description = "가게 정보",
                example = "{"
                        + "\"id\": 1, "
                        + "\"ownerId\": 2, "
                        + "\"name\": \"SSAFY Cafe\", "
                        + "\"address\": \"서울특별시 강남구 테헤란로 212\", "
                        + "\"latitude\": \"37.5010\", "
                        + "\"longitude\": \"127.0396\", "
                        + "\"type\": \"카페\", "
                        + "\"bankAccount\": \"123-456-78901234\", "
                        + "\"bankName\": \"국민은행\", "
                        + "\"ownerWord\": \"정성을 다하는 가게\", "
                        + "\"status\": \"운영 중\""
                        + "}")
        Integer id,
        String name,
        String address,
        String latitude,
        String longitude,
        String type,
        String bankAccount,
        String bankName,
        String ownerWord,
        String status,
        Integer userId
) {
        // Store 객체를 받는 생성자
        public StoreResponse(Store store) {
                this(
                        store.getId(),
                        store.getName(),
                        store.getAddress(),
                        store.getLatitude(),
                        store.getLongitude(),
                        store.getType(),
                        store.getBankAccount(),
                        store.getBankName(),
                        store.getOwnerWord(),
                        store.getStatus(),
                        store.getUserId()
                );
        }
}