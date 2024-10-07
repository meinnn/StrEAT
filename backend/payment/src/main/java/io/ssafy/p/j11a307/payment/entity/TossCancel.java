package io.ssafy.p.j11a307.payment.entity;

import io.ssafy.p.j11a307.payment.dto.CancelDetail;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TossCancel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String cancelReason;
    private String canceledAt;
    private Integer cancelAmount;
    private Integer taxFreeAmount;
    private Integer taxExemptionAmount;
    private Integer refundableAmount;
    private Integer easyPayDiscountAmount;
    private String transactionKey;
    private String receiptKey;
    private String cancelStatus;
    private String cancelRequestId;

    public TossCancel(CancelDetail cancelDetail) {
        this.cancelReason = cancelDetail.cancelReason();
        this.canceledAt = cancelDetail.canceledAt();
        this.cancelAmount = cancelDetail.cancelAmount();
        this.taxFreeAmount = cancelDetail.taxFreeAmount();
        this.taxExemptionAmount = cancelDetail.taxExemptionAmount();
        this.refundableAmount = cancelDetail.refundableAmount();
        this.easyPayDiscountAmount = cancelDetail.easyPayDiscountAmount();
        this.transactionKey = cancelDetail.transactionKey();
        this.receiptKey = cancelDetail.receiptKey();
        this.cancelStatus = cancelDetail.cancelStatus();
        this.cancelRequestId = cancelDetail.cancelRequestId();
    }
}
