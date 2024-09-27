package io.ssafy.p.j11a307.push_alert.dto.alerts;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FcmOrderStatusChangeAlert implements FcmAlertData {

    private String orderId;
    private String storeName;
    private String createdAt;
    private AlertType alertType;

    @Override
    public String getMessage() {
        return alertType.getMessage();
    }
}
