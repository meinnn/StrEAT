package io.ssafy.p.j11a307.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer customerId; //USER 마이크로서비스
    private Integer productId; //PRODUCT 마이크로서비스

    private Integer quantity;
    private Integer price;

    public void modifyOption(Integer price, Integer quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    // 개수 및 가격 업데이트 메서드
    public void updateQuantityAndPrice(Integer plusQuantity) {
        this.price += plusQuantity * (this.price/this.quantity);
        this.quantity += plusQuantity;
    }

    @Builder
    public ShoppingCart(Integer customerId, Integer productId, Integer quantity, Integer price) {
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
}
