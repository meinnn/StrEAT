package io.ssafy.p.j11a307.user.service;

import io.ssafy.p.j11a307.user.entity.Subscription;
import io.ssafy.p.j11a307.user.exception.BusinessException;
import io.ssafy.p.j11a307.user.exception.ErrorCode;
import io.ssafy.p.j11a307.user.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    @Value("{streat.internal-request}")
    private String internalRequestKey;

    private final UserService userService;
    private final FcmService fcmService;

    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    public void subscript(String accessToken, Integer storeId) {
        Integer userId = userService.getUserId(accessToken);
        Subscription.SubscriptionId subscriptionId = new Subscription.SubscriptionId(userId, storeId);
        fcmService.subscribeStore(storeId, internalRequestKey, userId);
        subscriptionRepository.save(new Subscription(subscriptionId));
    }

    @Transactional
    public void unsubscribe(String accessToken, Integer storeId) {
        Integer userId = userService.getUserId(accessToken);
        Subscription.SubscriptionId subscriptionId = new Subscription.SubscriptionId(userId, storeId);
        fcmService.unsubscribeStore(storeId, internalRequestKey, userId);
        subscriptionRepository.deleteById(subscriptionId);
    }

    @Transactional
    public void changeStoreAlertStatus(String accessToken, Integer storeId, boolean alertOn) {
        Integer userId = userService.getUserId(accessToken);
        if (!userService.isCustomer(userId)) {
            throw new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND);
        }
        // 찜한 가게가 아니면 알림 켜고 끌 수 없음
        Subscription.SubscriptionId subscriptionId = new Subscription.SubscriptionId(userId, storeId);
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_SUBSCRIBED_STORE));
        subscription.changeAlertStatus(alertOn);
        if (alertOn) {
            fcmService.subscribeStore(storeId, internalRequestKey, userId);
        } else {
            fcmService.unsubscribeStore(storeId, internalRequestKey, userId);
        }
    }
}
