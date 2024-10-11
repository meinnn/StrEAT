package io.ssafy.p.j11a307.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer productId; //외부 마이크로서비스
    private Integer count;

    @ManyToOne
    @JoinColumn(name="orders_id")
    private Orders ordersId;

    @Builder
    public OrderProduct(Integer productId, Integer count, Orders ordersId) {
        this.productId = productId;
        this.count = count;
        this.ordersId = ordersId;
    }
}
