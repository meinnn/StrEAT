package io.ssafy.p.j11a307.store.entity;

import io.ssafy.p.j11a307.store.exception.BusinessException;
import io.ssafy.p.j11a307.store.exception.ErrorCode;
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

    public void changeName(String newName) {
        if (newName == null || newName.isEmpty()) {
            throw new BusinessException(ErrorCode.INDUSTRY_CATEGORY_NAME_NULL); // 이름이 유효하지 않으면 예외 처리
        }
        this.name = newName;
    }
}
