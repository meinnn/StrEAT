package io.ssafy.p.j11a307.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class GetSalesListByTimeTypeProductDTO {
    private Integer quantity;
    private Double percent;
}
