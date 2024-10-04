package io.ssafy.p.j11a307.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ShoppingCartOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name= "shopping_cart_id")
    private ShoppingCart shoppingCartId;

    private Integer productOptionId; // PRODUCT 마이크로서비스

    @Builder
    public ShoppingCartOption(Integer id, ShoppingCart shoppingCartId, Integer productOptionId) {
        this.id = id;
        this.shoppingCartId = shoppingCartId;
        this.productOptionId = productOptionId;
    }
}
