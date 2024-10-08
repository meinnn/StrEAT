package io.ssafy.p.j11a307.user.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Subscription {

    @EmbeddedId
    private SubscriptionId subscriptionId;

    @ColumnDefault("true")
    private Boolean alertOn = true;

    public Subscription(SubscriptionId subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public void changeAlertStatus(Boolean alertOn) {
        this.alertOn = alertOn;
    }

    @Embeddable
    @NoArgsConstructor
    @EqualsAndHashCode
    @Getter
    public static class SubscriptionId implements Serializable {

        private Integer userId;
        private Integer storeId;

        public SubscriptionId(Integer userId, Integer storeId) {
            this.userId = userId;
            this.storeId = storeId;
        }
    }
}
