package io.ssafy.p.j11a307.push_alert.service;

import com.google.firebase.messaging.Notification;
import io.ssafy.p.j11a307.push_alert.dto.alerts.AlertType;
import io.ssafy.p.j11a307.push_alert.dto.alerts.FcmAlertData;
import io.ssafy.p.j11a307.push_alert.dto.alerts.FcmOrderStatusChangeAlert;
import io.ssafy.p.j11a307.push_alert.dto.alerts.FcmStoreOpenAlert;
import io.ssafy.p.j11a307.push_alert.util.FirebaseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final String TOPIC_STORE_PREFIX = "store-";

    @Value("${streat.internal-request}")
    private String internalRequestKey;

    private final UserService userService;

    private final FirebaseUtil firebaseUtil;

    public void sendOrderStatusChangeAlert(Integer customerId, Integer orderId, String storeName, AlertType alertType) {
        String customerFcmToken = userService.getFcmTokenByUserId(customerId, internalRequestKey);
        FcmAlertData data = FcmOrderStatusChangeAlert.builder()
                .orderId(String.valueOf(orderId))
                .storeName(storeName)
                .createdAt(convertDateFormat(new Date()))
                .alertType(alertType)
                .build();
        Notification notification = Notification.builder()
                .setTitle(data.getTitle())
                .setBody(data.getMessage())
                .build();

        firebaseUtil.pushAlertToClient(data, customerFcmToken, notification);
    }

    public void sendOpenStoreAlert(Integer storeId, String storeName, AlertType alertType) {
        String topic = TOPIC_STORE_PREFIX + storeId;
        FcmAlertData fcmAlertData = FcmStoreOpenAlert.builder()
                .storeId(String.valueOf(storeId))
                .storeName(storeName)
                .createdAt(convertDateFormat(new Date()))
                .alertType(alertType)
                .build();
        Notification notification = Notification.builder()
                .setTitle(fcmAlertData.getTitle())
                .setBody(fcmAlertData.getMessage())
                .build();
        firebaseUtil.pushAlertTopic(fcmAlertData, topic, notification);
    }

    public void subscribeToStore(Integer userId, Integer storeId) {
        String userFcmToken = userService.getFcmTokenByUserId(userId, internalRequestKey);
        firebaseUtil.subscribeTopic(TOPIC_STORE_PREFIX + storeId, userFcmToken);
    }

    public void unsubscribeFromStore(Integer userId, Integer storeId) {
        String userFcmToken = userService.getFcmTokenByUserId(userId, internalRequestKey);
        firebaseUtil.unsubscribeTopic(TOPIC_STORE_PREFIX + storeId, userFcmToken);
    }

    private String convertDateFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm");
        return simpleDateFormat.format(date);
    }
}
