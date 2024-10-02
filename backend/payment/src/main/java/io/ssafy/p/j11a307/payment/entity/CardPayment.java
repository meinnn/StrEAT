package io.ssafy.p.j11a307.payment.entity;

import io.ssafy.p.j11a307.payment.dto.CardPaymentRequest;
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
    public CardPayment(Payment payment, CardPaymentRequest cardPaymentRequest) {
        this.payment = payment;
        this.cardNumber = cardPaymentRequest.number();
        this.issuerCode = cardPaymentRequest.issuerCode();
        this.acquirerCode = cardPaymentRequest.acquireCode();
        this.installmentPlanMonths = cardPaymentRequest.installmentPlanMonths();
        this.isInterestFree = cardPaymentRequest.isInterestFree();
        this.interestPayer = cardPaymentRequest.interestPayer();
        this.approveNo = cardPaymentRequest.approveNo();
        this.useCardPoint = cardPaymentRequest.useCardPoint();
        this.cardType = cardPaymentRequest.cardType();
        this.ownerType = cardPaymentRequest.ownerType();
        this.acquireStatus = cardPaymentRequest.acquireStatus();
        this.amount = cardPaymentRequest.amount();
    }
}
