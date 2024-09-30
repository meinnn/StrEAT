package io.ssafy.p.j11a307.store.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

public class StorePhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(nullable = false)
    private String src;  // 사진 경로
}
