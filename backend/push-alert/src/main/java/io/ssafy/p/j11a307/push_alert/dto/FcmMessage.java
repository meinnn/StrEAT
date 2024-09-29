package io.ssafy.p.j11a307.push_alert.dto;

import io.ssafy.p.j11a307.push_alert.dto.alerts.FcmAlertData;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FcmMessage {

    private String token;
    private FcmAlertData data;
    private FcmNotification notification;
}
