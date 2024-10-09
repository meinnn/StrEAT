package io.ssafy.p.j11a307.order.global;

public enum OrderCode {
    WAITING_FOR_PAYING("결제 대기중"),
    REJECTED("거절됨"),
    WAITING_FOR_PROCESSING("대기중"),
    PROCESSING("조리중"),
    WAITING_FOR_RECEIPT("수령 대기중"),
    RECEIVED("수령 완료");

    private final String message;

    OrderCode(String message) {
        this.message = message;
    }
}
