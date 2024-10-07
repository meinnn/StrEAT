package io.ssafy.p.j11a307.product.entity;
import io.ssafy.p.j11a307.product.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ssafy.p.j11a307.product.exception.BusinessException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @Column(nullable = false)
    private String src;  // S3 이미지 URL 경로

    @Column(nullable = false)
    private LocalDateTime createdAt;  // 생성 시간 추가

    // 사진 경로를 변경하는 메서드
    public void changeSrc(String newSrc) {
        if (newSrc == null || newSrc.isEmpty()) {
            throw new BusinessException(ErrorCode.PRODUCT_PHOTO_NULL);
        }
        this.src = newSrc;
    }
}