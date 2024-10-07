package io.ssafy.p.j11a307.payment.global;

import lombok.Getter;

/**
 * 데이터 없이 응답 메시지만 필요한 경우 사용하는 응답 형태
 */
@Getter
public class MessageResponse {
    private final String message;

    // 생성자: 메시지 전달
    private MessageResponse(String message) {
        this.message = message;
    }

    // 메시지를 설정하는 정적 팩토리 메서드
    public static MessageResponse of(String message) {
        return new MessageResponse(message);
    }
}
