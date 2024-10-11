package io.ssafy.p.j11a307.push_alert.service;

import io.ssafy.p.j11a307.push_alert.dto.OrderStatusChangeRequest;
import io.ssafy.p.j11a307.push_alert.dto.PushAlertDetailResponse;
import io.ssafy.p.j11a307.push_alert.dto.PushAlertHistoryResponse;
import io.ssafy.p.j11a307.push_alert.dto.alerts.AlertType;
import io.ssafy.p.j11a307.push_alert.dto.alerts.FcmAlertData;
import io.ssafy.p.j11a307.push_alert.dto.alerts.FcmOrderStatusChangeAlert;
import io.ssafy.p.j11a307.push_alert.dto.alerts.FcmStoreOpenAlert;
import io.ssafy.p.j11a307.push_alert.dto.internalapi.ApiResponse;
import io.ssafy.p.j11a307.push_alert.dto.internalapi.FcmTokenResponse;
import io.ssafy.p.j11a307.push_alert.entity.PushAlert;
import io.ssafy.p.j11a307.push_alert.exception.BusinessException;
import io.ssafy.p.j11a307.push_alert.exception.ErrorCode;
import io.ssafy.p.j11a307.push_alert.repository.PushAlertRepository;
import io.ssafy.p.j11a307.push_alert.util.FirebaseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        PushAlert pushAlert = PushAlert.builder()
                .userId(orderStatusChangeRequest.customerId())
                .createdAt(creationTime)
                .title(data.getTitle())
                .message(data.getMessage())
                .orderId(orderStatusChangeRequest.orderId())
                .storeId(orderStatusChangeRequest.storeId())
                .build();

        if (customerFcmToken != null) {
            firebaseUtil.pushAlertToClient(data, customerFcmToken);
        }
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
        firebaseUtil.pushAlertTopic(fcmAlertData, topic);
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

    @Transactional
    public void checkAlert(Long alertId) {
        PushAlert pushAlert = pushAlertRepository.findById(alertId).orElseThrow(() -> new BusinessException(ErrorCode.ALERT_TYPE_NOT_FOUND));
        pushAlert.checkAlert();
    }

    @Transactional
    public PushAlertHistoryResponse getAllAlertsByUserId(String accessToken, Integer pgno, Integer spp) {
        Integer userId = userService.getUserId(accessToken, internalRequestKey);
        Pageable pageable = PageRequest.of(pgno, spp);
        Page<PushAlert> pushAlerts = pushAlertRepository.findByUserIdOrderByIdDesc(userId, pageable);
        List<PushAlertDetailResponse> pushAlertResponses = pushAlerts.stream().map(PushAlertDetailResponse::new).toList();
        Long totalDataCount = pushAlertRepository.countByUserId(userId);
        int totalPageCount = pushAlerts.getTotalPages();
        return new PushAlertHistoryResponse(pushAlertResponses, totalDataCount, totalPageCount);
    }

    public void turnOnAllDibsAlerts(String fcmToken, List<Integer> storeIds) {
        for (Integer storeId : storeIds) {
            firebaseUtil.subscribeTopic(TOPIC_STORE_PREFIX + storeId, fcmToken);
        }
    }

    public void turnOffAllDibsAlerts(String fcmToken, List<Integer> storeIds) {
        for (Integer storeId : storeIds) {
            firebaseUtil.unsubscribeTopic(TOPIC_STORE_PREFIX + storeId, fcmToken);
        }
    }

    private String convertDateFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm");
        return simpleDateFormat.format(date);
    }
}
