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
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 30)
    private String name;  // 세부 카테고리명 (예: 뷔페, 김밥/만두/분식 등)

    @Column(nullable = false, length = 10, unique = true)
    private String code;  // 세부 카테고리 코드 (예: I20701, I21001 등)

    @ManyToOne
    @JoinColumn(name = "top_category_id", nullable = false)
    private TopCategory topCategory;

    public void setTopCategory(TopCategory topCategory) {
        this.topCategory = topCategory;
    }

    public void changeName(String newName) {
        this.name = newName;
    }
}
