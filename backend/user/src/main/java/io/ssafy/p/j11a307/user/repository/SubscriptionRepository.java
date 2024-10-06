package io.ssafy.p.j11a307.user.repository;

import io.ssafy.p.j11a307.user.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Subscription.SubscriptionId> {
}
