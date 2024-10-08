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
public class TopCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;  // 주요 카테고리명 (예: 한식, 구내식당, 기타 등)

    @Column(nullable = false, unique = true)
    private String code;  // 주요 카테고리 코드 (예: I201, I207 등)

    @OneToMany(mappedBy = "topCategory", cascade = CascadeType.ALL)
    private List<SubCategory> subCategories;

    public void addSubCategory(SubCategory subCategory) {
        this.subCategories.add(subCategory);
        subCategory.setTopCategory(this);
    }

    public void changeName(String newName) {
        this.name = newName;
    }
}
