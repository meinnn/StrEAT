package io.ssafy.p.j11a307.push_alert.dto.alerts;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

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

    @Override
    public String getTitle() {
        return storeName;
    }

    @Override
    public Map<String, String> getData() {
        return Map.of(
                "orderId", orderId,
                "storeName", storeName,
                "createdAt", createdAt
        );
    }
}
