package io.ssafy.p.j11a307.order.entity;

import io.ssafy.p.j11a307.order.global.OrderCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    private String orderNumber;

    @Enumerated(EnumType.STRING)
    private OrderCode status;
    private String request;

    @CreatedDate
    private LocalDateTime createdAt;

    private Integer totalPrice;
    private String paymentMethod;
    private LocalDateTime paidAt;
    private LocalDateTime receivedAt;
    private String phone;

    public void updateStatus(OrderCode status) {
        this.status = status;
    }
}
