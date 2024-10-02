package io.ssafy.p.j11a307.user.service;

import io.ssafy.p.j11a307.user.entity.Subscription;
import io.ssafy.p.j11a307.user.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final UserService userService;

    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    public void subscript(String accessToken, Integer storeId) {
        Integer userId = userService.getUserId(accessToken);
        Subscription.SubscriptionId subscriptionId = new Subscription.SubscriptionId(userId, storeId);
        subscriptionRepository.save(new Subscription(subscriptionId));
    }

    @Transactional
    public void unsubscribe(String accessToken, Integer storeId) {
        Integer userId = userService.getUserId(accessToken);
        Subscription.SubscriptionId subscriptionId = new Subscription.SubscriptionId(userId, storeId);
        subscriptionRepository.deleteById(subscriptionId);
    }
}
