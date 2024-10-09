package io.ssafy.p.j11a307.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderProductOption {
    @EmbeddedId
    private OrdersUserId orderProductId;

    @Builder
    public OrderProductOption(OrdersUserId orderProductId) {
        this.orderProductId = orderProductId;
    }

    //    public Integer getProductOptionId() {
//        return this.orderProductId.getProductOptionId();
//    }
}
