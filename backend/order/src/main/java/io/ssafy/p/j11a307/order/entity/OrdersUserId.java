package io.ssafy.p.j11a307.order.entity;


import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Embeddable
@Getter
public class OrdersUserId {
    @ManyToOne
    @JoinColumn(name="order_product_id")
    private OrderProduct orderProductId;

    private Integer productOptionId; //외부 마이크로서비스

    public OrdersUserId() {}
}

