package io.ssafy.p.j11a307.payment.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "payment")
    private CardPayment cardPayment;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "payment")
    private TossEasyPayment tossEasyPayment;

    private String mid;
    private String version;
    private String paymentKey;
    private String status;
    private String lastTransactionKey;
    private String tossOrderId;
    private String tossOrderName;
    private String requestedAt;
    private String approvedAt;
    private Boolean useEscrow;
    private Boolean cultureExpense;
    private String virtualAccount;
    private String transfer;
    private String mobilePhone;
    private String giftCertificate;
    private String cashReceipt;
    private String cashReceipts;
    private String discount;
    private String cancels;
    private String secret;
    private String type;
    private String country;
    private String failure;
    private Boolean isPartialCancelable;
    private String receiptUrl; // receipt.url을 변환
    private String checkoutUrl; // checkout.url을 변환
    private String currency;
    private Integer totalAmount;
    private Integer balanceAmount;
    private Integer suppliedAmount;
    private Integer vat;
    private Integer taxFreeAmount;
    private Integer taxExemptionAmount;
    private String method;

    public void addCardPayment(CardPayment cardPayment) {
        this.cardPayment = cardPayment;
    }
}
