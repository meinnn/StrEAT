package io.ssafy.p.j11a307.user.repository;

import io.ssafy.p.j11a307.user.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Subscription.SubscriptionId> {

    List<Subscription> findBySubscriptionIdUserId(Integer userId);
    List<Subscription> findBySubscriptionIdStoreId(Integer storeId);
}
