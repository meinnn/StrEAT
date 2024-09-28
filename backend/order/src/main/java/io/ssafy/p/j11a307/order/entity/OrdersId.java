package io.ssafy.p.j11a307.order.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Embeddable
public class OrdersId implements Serializable {
    @ManyToOne
    @JoinColumn(name="review_id")
    private Orders id;

    public OrdersId() {}

    public OrdersId(Orders id) {
        this.id = id;
    }
}
