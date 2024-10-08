package io.ssafy.p.j11a307.push_alert.service;

import com.google.firebase.messaging.Notification;
import io.ssafy.p.j11a307.push_alert.dto.OrderStatusChangeRequest;
import io.ssafy.p.j11a307.push_alert.entity.PushAlert;
import io.ssafy.p.j11a307.push_alert.global.DataResponse;
import io.ssafy.p.j11a307.push_alert.repository.PushAlertRepository;
import io.ssafy.p.j11a307.push_alert.dto.alerts.AlertType;
import io.ssafy.p.j11a307.push_alert.dto.alerts.FcmAlertData;
import io.ssafy.p.j11a307.push_alert.dto.alerts.FcmOrderStatusChangeAlert;
import io.ssafy.p.j11a307.push_alert.dto.alerts.FcmStoreOpenAlert;
import io.ssafy.p.j11a307.push_alert.dto.internalapi.ApiResponse;
import io.ssafy.p.j11a307.push_alert.dto.internalapi.FcmTokenResponse;
import io.ssafy.p.j11a307.push_alert.util.FirebaseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final String TOPIC_STORE_PREFIX = "store-";

    @Value("${streat.internal-request}")
    private String internalRequestKey;

    private final UserService userService;

    private final PushAlertRepository pushAlertRepository;

    private final FirebaseUtil firebaseUtil;

    public void sendOrderStatusChangeAlert(OrderStatusChangeRequest orderStatusChangeRequest, AlertType alertType) {
        ApiResponse<FcmTokenResponse> fcmTokenResponse =
                userService.getFcmTokenByUserId(orderStatusChangeRequest.customerId(), internalRequestKey);
        String customerFcmToken = fcmTokenResponse.getData().fcmToken();
        String creationTime = convertDateFormat(new Date());
        FcmAlertData data = FcmOrderStatusChangeAlert.builder()
                .orderId(String.valueOf(orderStatusChangeRequest.orderId()))
                .storeName(orderStatusChangeRequest.storeName())
                .createdAt(creationTime)
                .alertType(alertType)
                .build();
        Notification notification = Notification.builder()
                .setTitle(data.getTitle())
                .setBody(data.getMessage())
                .build();

        PushAlert pushAlert = PushAlert.builder()
                .userId(orderStatusChangeRequest.customerId())
                .createdAt(creationTime)
                .title(data.getTitle())
                .message(data.getMessage())
                .orderId(orderStatusChangeRequest.orderId())
                .storeId(orderStatusChangeRequest.storeId())
                .build();

        firebaseUtil.pushAlertToClient(data, customerFcmToken, notification);
        pushAlertRepository.save(pushAlert);
    }

    public void sendOpenStoreAlert(Integer storeId, String storeName, AlertType alertType) {
        String topic = TOPIC_STORE_PREFIX + storeId;
        String creationTime = convertDateFormat(new Date());
        FcmAlertData fcmAlertData = FcmStoreOpenAlert.builder()
                .storeId(String.valueOf(storeId))
                .storeName(storeName)
                .createdAt(creationTime)
                .alertType(alertType)
                .build();
        Notification notification = Notification.builder()
                .setTitle(fcmAlertData.getTitle())
                .setBody(fcmAlertData.getMessage())
                .build();
        List<Integer> dibsUserIds = userService.getCalledDibsUserByStoreId(storeId, internalRequestKey).getData();
        // push alert 데이터 생성
        List<PushAlert> pushAlerts = dibsUserIds.stream().map(id ->
                PushAlert.builder()
                        .userId(id)
                        .storeId(storeId)
                        .createdAt(creationTime)
                        .title(fcmAlertData.getTitle())
                        .message(fcmAlertData.getMessage())
                        .build()).toList();
        firebaseUtil.pushAlertTopic(fcmAlertData, topic, notification);
        pushAlertRepository.saveAll(pushAlerts);
    }

    public void subscribeToStore(Integer userId, Integer storeId) {
        ApiResponse<FcmTokenResponse> fcmTokenResponse = userService.getFcmTokenByUserId(userId, internalRequestKey);
        String userFcmToken = fcmTokenResponse.getData().fcmToken();
        firebaseUtil.subscribeTopic(TOPIC_STORE_PREFIX + storeId, userFcmToken);
    }

    public void unsubscribeFromStore(Integer userId, Integer storeId) {
        ApiResponse<FcmTokenResponse> fcmTokenResponse = userService.getFcmTokenByUserId(userId, internalRequestKey);
        String userFcmToken = fcmTokenResponse.getData().fcmToken();
        firebaseUtil.unsubscribeTopic(TOPIC_STORE_PREFIX + storeId, userFcmToken);
    }

    private String convertDateFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm");
        return simpleDateFormat.format(date);
    }
}
