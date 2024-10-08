package io.ssafy.p.j11a307.order.global;

public enum PayTypeCode {
    CREDIT_CARD("신용카드"),
    CASH("현금"),
    SIMPLE_PAYMENT("간편결제"),
    ACCOUNT_TRANSFER("계좌이체");

    private final String message;

    PayTypeCode(String message) {
        this.message = message;
    }
}
