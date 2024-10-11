package io.ssafy.p.j11a307.push_alert.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.TopicManagementResponse;
import io.ssafy.p.j11a307.push_alert.dto.OrderStatusChangeRequest;
import io.ssafy.p.j11a307.push_alert.dto.alerts.AlertType;
import io.ssafy.p.j11a307.push_alert.dto.alerts.FcmAlertData;
import io.ssafy.p.j11a307.push_alert.dto.alerts.FcmOrderStatusChangeAlert;
import io.ssafy.p.j11a307.push_alert.dto.internalapi.ApiResponse;
import io.ssafy.p.j11a307.push_alert.dto.internalapi.FcmTokenResponse;
import io.ssafy.p.j11a307.push_alert.service.AlertService;
import io.ssafy.p.j11a307.push_alert.service.UserService;
import io.ssafy.p.j11a307.push_alert.util.FirebaseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.lenient;

public class FcmAlertTest {

    @Spy
    private FirebaseUtil firebaseUtil;

    @Mock
    private UserService userService;

    @InjectMocks
    private AlertService alertService;

    String fcmToken = "f13CxSVXqOGFBF_012Q3C9:APA91bE5XjoQ2yHrZ_zo7f5qBOLNq6qu2mr5dDilGcNCyDmh_dsvISqyTlxUSlL6Mdsbu-F0f9xYrgWZ1Azv8HedlEq3GMxFUiaadtxSUcm6kM3VBQPFnrh1yEJKOate_y-WfbNOYEQX";
    String fcmToken2 = "c1SEfUODpS1WUWwxWmpkfk:APA91bFXcbJv1LqsJ4G4kxAl2-G8VQ3NHCnQIgqyMCIl2mSLiDBpc7lSkBaisSOdf3XZMjVJ3wIMIPtNS8iusAmmbBvVnEhBNzL3sHvHTRAM2cL_GF2pJVkFYTM-MPqPpdt5nob1SoN9";
    String topic = "store-7";

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        String firebaseApiUrl = "https://fcm.googleapis.com/v1/projects/streat-c2387/messages:send";
        String firebaseConfigPath = "fcm-config.json";

        String internalRequestKey = "streat-internal-request";

        ReflectionTestUtils.setField(firebaseUtil, "firebaseApiUrl", firebaseApiUrl);
        ReflectionTestUtils.setField(firebaseUtil, "firebaseConfigPath", firebaseConfigPath);
        ReflectionTestUtils.setField(alertService, "internalRequestKey", internalRequestKey);
        FcmTokenResponse data = new FcmTokenResponse(fcmToken2);
        ApiResponse<FcmTokenResponse> fcmTokenResponse = ApiResponse.<FcmTokenResponse>builder().data(data).message("message").build();

        lenient().when(userService.getFcmTokenByUserId(1, internalRequestKey)).thenReturn(fcmTokenResponse);

        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream());

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();

        FirebaseApp.initializeApp(options);
    }

    @Test
    void statusChangeAlertTest() {
        Integer userId = 1;
        Integer orderId = 2;
        String storeName = "스토어 네임";
        AlertType alertType = AlertType.ORDER_REQUESTED;
        OrderStatusChangeRequest orderStatusChangeRequest = new OrderStatusChangeRequest(userId, orderId, storeName, 1);
        alertService.sendOrderStatusChangeAlert(orderStatusChangeRequest, alertType);
    }

    @Test
    void sendToClientTest() throws IOException {
        FcmAlertData fcmAlertData = FcmOrderStatusChangeAlert.
                builder().orderId("1").storeName("유경이네 보쌈").createdAt("now")
                .alertType(AlertType.ORDER_REQUESTED).build();
        firebaseUtil.pushAlertToClient(fcmAlertData, fcmToken2);
    }

    @Test
    void sendToTopicTest() {
        FcmAlertData fcmAlertData = FcmOrderStatusChangeAlert.
                builder().orderId("1").storeName("u gang").createdAt("now")
                .alertType(AlertType.ORDER_REQUESTED).build();
        firebaseUtil.pushAlertTopic(fcmAlertData, topic);
    }

    @Test
    void subscribeTopic() {
//        firebaseUtil.subscribeTopic(topic, fcmToken2);
        FcmAlertData fcmAlertData = FcmOrderStatusChangeAlert.
                builder().orderId("1").storeName("topic store").createdAt("now")
                .alertType(AlertType.ORDER_REQUESTED).build();
        firebaseUtil.pushAlertTopic(fcmAlertData, topic);
    }

    @Test
    void unsubscribeTopic() {
        firebaseUtil.unsubscribeTopic(topic, fcmToken2);
    }
}
