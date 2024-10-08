package io.ssafy.p.j11a307.push_alert.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PushAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer userId;
    private Integer storeId;
    private String createdAt;
    private String title;
    private String message;
    private Integer orderId;
    @ColumnDefault("false")
    private Boolean checked = false;

    @Builder
    public PushAlert(Integer userId, Integer storeId, String createdAt, String title, String message, Integer orderId, Boolean checked) {
        this.userId = userId;
        this.storeId = storeId;
        this.createdAt = createdAt;
        this.title = title;
        this.message = message;
        this.orderId = orderId;
        this.checked = checked;
    }

    public void checkAlert() {
        this.checked = true;
    }
}
