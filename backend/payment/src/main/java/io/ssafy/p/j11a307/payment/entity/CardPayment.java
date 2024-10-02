package io.ssafy.p.j11a307.payment.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardPayment {

    @Id
    private Integer id;

    @OneToOne
    @MapsId // Payment의 ID를 이 엔티티의 기본 키로 사용
    @JoinColumn(name = "id")
    private Payment payment;

    private String cardNumber;
    private String issuerCode;
    private String acquirerCode;
    private Integer installmentPlanMonths;
    private Boolean isInterestFree;
    private String interestPayer;
    private String approveNo;
    private Boolean useCardPoint;
    private String cardType;
    private String ownerType;
    private String acquireStatus;
    private Integer amount;

    @Builder
    public CardPayment(Payment payment, JsonNode cardNode) {
        this.payment = payment;
        this.cardNumber = cardNode.get("cardNumber").asText(); // 카드 번호
        this.issuerCode = cardNode.get("issuerCode").asText(); // 발급사 코드
        this.acquirerCode = cardNode.get("acquirerCode").asText(); // 매입사 코드
        this.installmentPlanMonths = cardNode.get("installmentPlanMonths").asInt();
        this.isInterestFree = cardNode.get("isInterestFree").asBoolean();
        this.interestPayer = cardNode.get("interestPayer").asText(); // 이자 부담 주체
        this.approveNo = cardNode.get("approveNo").asText(); // 승인 번호
        this.useCardPoint = cardNode.get("useCardPoint").asBoolean();
        this.cardType = cardNode.get("cardType").asText(); // 카드 유형
        this.ownerType = cardNode.get("ownerType").asText(); // 카드 소유자 유형
        this.acquireStatus = cardNode.get("acquireStatus").asText(); // 매입 상태
        this.amount = cardNode.get("amount").asInt();
    }
}
