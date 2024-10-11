package io.ssafy.p.j11a307.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.List;

public record ResponseData2(
        Meta meta,
        List<Document> documents
) {
    public static record Meta(
            SameName same_name,
            int pageable_count,
            int total_count,
            boolean is_end
    ) {}

    public static record SameName(
            List<String> region,
            String keyword,
            String selected_region
    ) {}

    public static record Document(
            String place_name,
            String distance,
            String place_url,
            String category_name,
            String address_name,
            String road_address_name,
            String id,
            String phone,
            String category_group_code,
            String category_group_name,
            String x,
            String y
    ) {}
}
