package io.ssafy.p.j11a307.order.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Review {
    @EmbeddedId
    private OrdersId id;

    private Integer score;
    private String content;

    @Builder
    public Review(OrdersId id, Integer score, String content) {
        this.id = id;
        this.score = score;
        this.content = content;
    }
}
