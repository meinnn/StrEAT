package io.ssafy.p.j11a307.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name= "review_id")
    private Review reviewId;

    private String src;

    @Builder
    public ReviewPhoto(Review reviewId, String src) {
        this.reviewId = reviewId;
        this.src = src;
    }
}
