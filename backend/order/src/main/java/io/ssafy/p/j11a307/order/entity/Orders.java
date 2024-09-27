package io.ssafy.p.j11a307.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId; //외부 마이크로서비스
    private Integer storeId; //외부 마이크로서비스

    private String orderNumber;
    private String status;
    private String request;

    @CreatedDate
    private LocalDateTime createdAt;

    private Integer totalPrice;
    private String paymentMethod;
    private LocalDateTime paidAt;
    private LocalDateTime receivedAt;
    private String phone;
}
