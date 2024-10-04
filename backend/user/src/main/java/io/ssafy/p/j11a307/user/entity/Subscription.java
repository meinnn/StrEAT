package io.ssafy.p.j11a307.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Subscription {

    @EmbeddedId
    private SubscriptionId subscriptionId;

    public Subscription(SubscriptionId subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    @Embeddable
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class SubscriptionId implements Serializable {

        private Integer userId;
        private Integer storeId;

        public SubscriptionId(Integer userId, Integer storeId) {
            this.userId = userId;
            this.storeId = storeId;
        }
    }
}
