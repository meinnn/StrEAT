package io.ssafy.p.j11a307.push_alert.dto.alerts;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class FcmStoreOpenAlert implements FcmAlertData {

    private String storeId;
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
    public String getLink() {
        return "/customer/stores/" + storeId;
    }

    @Override
    public Map<String, String> getData() {
        return Map.of(
                "storeId", storeId,
                "storeName", storeName,
                "createdAt", createdAt,
                "title", alertType.getMessage(),
                "message", storeName
        );
    }
}
