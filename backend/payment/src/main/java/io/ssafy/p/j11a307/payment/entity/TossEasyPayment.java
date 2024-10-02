package io.ssafy.p.j11a307.payment.entity;

import com.fasterxml.jackson.databind.JsonNode;
import io.ssafy.p.j11a307.payment.dto.TossEasyPaymentRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TossEasyPayment {

    @Id
    private Integer id;

    @OneToOne
    @MapsId // Payment의 ID를 이 엔티티의 기본 키로 사용
    @JoinColumn(name = "id")
    private Payment payment;

    private String provider;
    private Integer amount;
    private Integer discountAmount;

    @Builder
    public TossEasyPayment(Payment payment, JsonNode easyPaymentNode) {
        this.payment = payment;
        this.provider = easyPaymentNode.get("provider").asText();
        this.amount = easyPaymentNode.get("amount").asInt();
        this.discountAmount = easyPaymentNode.get("discountAmount").asInt();
    }
}
