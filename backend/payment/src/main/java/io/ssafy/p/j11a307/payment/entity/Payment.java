package io.ssafy.p.j11a307.payment.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TossCancel> tossCancels;

    private String mid;
    private String version;
    private String paymentKey;
    private String status;
    private String lastTransactionKey;
    private String orderId;
    private String orderName;
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

    public void addTossEasyPayPayment(TossEasyPayment tossEasyPayment) {
        this.tossEasyPayment = tossEasyPayment;
    }

    public void addTossCancels(List<TossCancel> tossCancels) {
        this.tossCancels = tossCancels;
    }

    public Payment(JsonNode jsonNode) {
        this.mid = jsonNode.get("mId").asText();
        this.version = jsonNode.get("version").asText();
        this.paymentKey = jsonNode.get("paymentKey").asText();
        this.status = jsonNode.get("status").asText();
        this.lastTransactionKey = jsonNode.get("lastTransactionKey").asText();
        this.orderId = jsonNode.get("orderId").asText();
        this.orderName = jsonNode.get("orderName").asText();
        this.requestedAt = jsonNode.get("requestedAt").asText();
        this.approvedAt = jsonNode.get("approvedAt").asText();
        this.useEscrow = jsonNode.get("useEscrow").asBoolean();
        this.cultureExpense = jsonNode.get("cultureExpense").asBoolean();
        this.virtualAccount = jsonNode.get("virtualAccount").asText();
        this.transfer = jsonNode.get("transfer").asText();
        this.mobilePhone = jsonNode.get("mobilePhone").asText();
        this.giftCertificate = jsonNode.get("giftCertificate").asText();
        this.cashReceipt = jsonNode.get("cashReceipt").asText();
        this.cashReceipts = jsonNode.get("cashReceipts").asText();
        this.discount = jsonNode.get("discount").asText();
        this.cancels = jsonNode.get("cancels").asText();
        this.secret = jsonNode.get("secret").asText();
        this.type = jsonNode.get("type").asText();
        this.country = jsonNode.get("country").asText();
        this.failure = jsonNode.get("failure").asText();
        this.isPartialCancelable = jsonNode.get("isPartialCancelable").asBoolean();

        // 중첩된 필드 처리
        this.receiptUrl = jsonNode.get("receipt").get("url").asText();
        this.checkoutUrl = jsonNode.get("checkout").get("url").asText();

        this.currency = jsonNode.get("currency").asText();
        this.totalAmount = jsonNode.get("totalAmount").asInt();
        this.balanceAmount = jsonNode.get("balanceAmount").asInt();
        this.suppliedAmount = jsonNode.get("suppliedAmount").asInt();
        this.vat = jsonNode.get("vat").asInt();
        this.taxFreeAmount = jsonNode.get("taxFreeAmount").asInt();
        this.taxExemptionAmount = jsonNode.get("taxExemptionAmount").asInt();
        this.method = jsonNode.get("method").asText();
    }
}
