package io.ssafy.p.j11a307.store.dto;

import io.ssafy.p.j11a307.store.entity.StoreStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;

import java.util.List;

public record ReadNearByStoreDTO(
        Integer id,
        String storeName, //가게 이름
        String src, //가게 위치 사진
        StoreStatus status,
        List<String> categories, //해당 가게의 음식 카테고리
        Integer distance, //내 위치로부터 얼마나 떨어져있는지
        Double latitude,
        Double longitude
) {
}