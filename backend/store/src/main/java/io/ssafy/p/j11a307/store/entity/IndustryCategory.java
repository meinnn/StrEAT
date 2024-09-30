package io.ssafy.p.j11a307.store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndustryCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 20)
    private String name;  // 업종명

    @OneToMany(mappedBy = "industryCategory", cascade = CascadeType.ALL)
    private List<StoreIndustryCategory> storeIndustryCategories;
}
