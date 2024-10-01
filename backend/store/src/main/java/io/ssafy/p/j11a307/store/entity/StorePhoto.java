package io.ssafy.p.j11a307.store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StorePhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(nullable = false)
    private String src;  // 사진 경로

    // 사진 경로를 변경하는 메서드
    public void changeSrc(String newSrc) {
        if (newSrc == null || newSrc.isEmpty()) {
            throw new IllegalArgumentException("사진 경로는 비어 있을 수 없습니다.");
        }
        this.src = newSrc;
    }
}
