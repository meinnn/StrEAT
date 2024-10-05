package io.ssafy.p.j11a307.push_alert.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import io.ssafy.p.j11a307.push_alert.dto.FcmAlert;
import io.ssafy.p.j11a307.push_alert.dto.FcmMessage;
import io.ssafy.p.j11a307.push_alert.dto.FcmNotification;
import io.ssafy.p.j11a307.push_alert.dto.alerts.FcmAlertData;
import io.ssafy.p.j11a307.push_alert.exception.BusinessException;
import io.ssafy.p.j11a307.push_alert.exception.ErrorCode;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

    public void pushAlert(FcmAlertData fcmAlertData, String receiverFcmToken, FcmNotification fcmNotification) {
        try {
            String message = createMessage(fcmAlertData, receiverFcmToken, fcmNotification);
            RestTemplate restTemplate = new RestTemplate();

            // 한글 깨짐 증상에 대한 수정
            restTemplate.getMessageConverters()
                    .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + getAccessToken());

            HttpEntity<String> entity = new HttpEntity<>(message, headers);
            try {
                ResponseEntity<String> response = restTemplate.exchange(firebaseApiUrl, HttpMethod.POST, entity, String.class);
            } catch (HttpClientErrorException e) {
            }
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.PUSH_ALERT_FAILED);
        }
    }

    private String getAccessToken() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    private String createMessage(FcmAlertData fcmAlertData, String receiverFcmToken, FcmNotification notification)
            throws JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .token(receiverFcmToken)
                .data(fcmAlertData)
                .notification(notification)
                .build();

        FcmAlert alertDto = FcmAlert.builder()
                .message(fcmMessage)
                .build();

        return new ObjectMapper().writeValueAsString(alertDto);
    }
}
