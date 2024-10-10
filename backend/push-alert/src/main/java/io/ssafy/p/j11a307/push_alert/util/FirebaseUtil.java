package io.ssafy.p.j11a307.push_alert.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import io.ssafy.p.j11a307.push_alert.dto.alerts.FcmAlertData;
import io.ssafy.p.j11a307.push_alert.exception.BusinessException;
import io.ssafy.p.j11a307.push_alert.exception.ErrorCode;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class FirebaseUtil {

    @Value("${firebase.api-url}")
    private String firebaseApiUrl;

    @Value("${firebase.config-path}")
    private String firebaseConfigPath;

    @PostConstruct
    public void initializeFirebase() throws IOException {
        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream());

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();

        FirebaseApp.initializeApp(options);
    }

    public void pushAlertToClient(FcmAlertData fcmAlertData, String receiverFcmToken) {
        WebpushConfig webpushConfig = createWebpushConfig(fcmAlertData);
        Message message = Message.builder()
                .setToken(receiverFcmToken)
                .setWebpushConfig(webpushConfig)
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            throw new BusinessException(ErrorCode.PUSH_ALERT_FAILED);
        }
    }

    public void pushAlertTopic(FcmAlertData fcmAlertData, String topic) {
        WebpushConfig webpushConfig = createWebpushConfig(fcmAlertData);
        Message message = Message.builder()
                .setTopic(topic)
                .setWebpushConfig(webpushConfig)
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            throw new BusinessException(ErrorCode.PUSH_ALERT_FAILED);
        }
    }

    public void subscribeTopic(String topic, String token) {
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(List.of(token), topic);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void unsubscribeTopic(String topic, String token) {
        try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(List.of(token), topic);
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private WebpushConfig createWebpushConfig(FcmAlertData fcmAlertData) {
        WebpushFcmOptions webpushFcmOptions = WebpushFcmOptions.builder()
                .setLink(fcmAlertData.getLink())
                .build();
        WebpushNotification webpushNotification = WebpushNotification.builder()
                .setTitle(fcmAlertData.getTitle())
                .setBody(fcmAlertData.getMessage())
                .setIcon("/web-app-manifest-192x192.png")
                .build();
        return WebpushConfig.builder()
                .setFcmOptions(webpushFcmOptions)
                .setNotification(webpushNotification)
                .putAllData(fcmAlertData.getData())
                .build();
    }
}
