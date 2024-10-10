package io.ssafy.p.j11a307.order.dto;

import lombok.Builder;

@Builder
public record GroupDTO(
        String tag,
        String bestGroup,
        Double bestDegree,
        String worstGroup,
        Double worstDegree,
        Double average
) {
}
