package io.ssafy.p.j11a307.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderProductOption {
    @EmbeddedId
    private OrdersUserId orderProductId;
}
