package io.ssafy.p.j11a307.order.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Review {
    @EmbeddedId
    private OrdersId id;

    private Integer score;
    private String content;

    private LocalDateTime createdAt;

    @Builder
    public Review(OrdersId id, Integer score, String content, LocalDateTime createdAt) {
        this.id = id;
        this.score = score;
        this.content = content;
        this.createdAt = createdAt;
    }
}
