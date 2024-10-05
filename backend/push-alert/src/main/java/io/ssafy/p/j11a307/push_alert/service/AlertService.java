package io.ssafy.p.j11a307.push_alert.service;

import io.ssafy.p.j11a307.push_alert.dto.FcmNotification;
import io.ssafy.p.j11a307.push_alert.dto.alerts.AlertType;
import io.ssafy.p.j11a307.push_alert.dto.alerts.FcmAlertData;
import io.ssafy.p.j11a307.push_alert.dto.alerts.FcmOrderStatusChangeAlert;
import io.ssafy.p.j11a307.push_alert.util.FirebaseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AlertService {

    @Value("{streat.internal-request}")
    private String internalRequestKey;

    private final UserService userService;

    private final FirebaseUtil firebaseUtil;

    public void sendOrderStatusChangeAlert(Integer customerId, Integer orderId, String storeName, AlertType alertType) {
        String customerFcmToken = userService.getFcmTokenByUserId(customerId, internalRequestKey);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm");
        FcmAlertData data = FcmOrderStatusChangeAlert.builder()
                .orderId(String.valueOf(orderId))
                .storeName(storeName)
                .createdAt(simpleDateFormat.format(new Date()))
                .alertType(alertType)
                .build();
        FcmNotification notification = FcmNotification.builder()
                .body(data.getMessage())
                .build();

        firebaseUtil.pushAlertToClient(data, customerFcmToken);
    }
}
