package io.ssafy.p.j11a307.order.entity;

import io.ssafy.p.j11a307.order.global.OrderCode;
import io.ssafy.p.j11a307.order.global.PayTypeCode;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId; //외부 마이크로서비스
    private Integer storeId; //외부 마이크로서비스
    private Integer storeSimpleLocationId; //외부 마이크로서비스

    private String orderNumber;

    @Enumerated(EnumType.STRING)
    private OrderCode status;
    private String request;

    //@CreatedDate
    private LocalDateTime createdAt;

    private Integer totalPrice;

    @Enumerated(EnumType.STRING)
    private PayTypeCode paymentMethod;
    private LocalDateTime paidAt;
    private LocalDateTime receivedAt;
    private String phone;

    public void updateStatus(OrderCode status) {
        this.status = status;
    }


    @Builder
    public Orders(Integer storeSimpleLocationId, Integer userId, Integer storeId, LocalDateTime createdAt, String orderNumber, OrderCode status, String request, Integer totalPrice, PayTypeCode paymentMethod, LocalDateTime paidAt, LocalDateTime receivedAt, String phone) {
        this.storeSimpleLocationId = storeSimpleLocationId;
        this.userId = userId;
        this.storeId = storeId;
        this.orderNumber = orderNumber;
        this.createdAt = createdAt;
        this.status = status;
        this.request = request;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.paidAt = paidAt;
        this.receivedAt = receivedAt;
        this.phone = phone;
    }
}
