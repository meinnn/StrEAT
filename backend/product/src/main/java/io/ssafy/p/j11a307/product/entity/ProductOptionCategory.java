package io.ssafy.p.j11a307.product.entity;

import io.ssafy.p.j11a307.product.dto.UpdateProductOptionCategoryDTO;
import io.ssafy.p.j11a307.product.exception.BusinessException;
import io.ssafy.p.j11a307.product.exception.ErrorCode;
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
public class ProductOptionCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String name;
    private Boolean isEssential;
    private Integer maxSelect;
    private Integer minSelect;

    @Getter
    @OneToMany(mappedBy = "productOptionCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> options;

    // 옵션 카테고리명 변경 메서드
    public void changeName(String name) {
        if (name == null || name.isEmpty()) {
            throw new BusinessException(ErrorCode.PRODUCT_OPTION_CATEGORY_NAME_NULL);
        }
        this.name = name;
    }

    // 필수 여부 변경 메서드
    public void changeIsEssential(Boolean isEssential) {
        this.isEssential = isEssential;
    }

    // 최대 선택 개수 변경 메서드
    public void changeMaxSelect(Integer maxSelect) {
        if (maxSelect == null || maxSelect < 0) {
            throw new BusinessException(ErrorCode.INVALID_MAX_SELECT);
        }
        this.maxSelect = maxSelect;
    }

    // 최대 선택 개수 변경 메서드
    public void changeMinSelect(Integer minSelect) {
        if (minSelect == null || minSelect < 0) {
            throw new BusinessException(ErrorCode.INVALID_MIN_SELECT);
        }
        this.minSelect = minSelect;
    }

    public ProductOptionCategory(Product product, UpdateProductOptionCategoryDTO dto) {
        this.product = product;
        this.name = dto.name();
        this.isEssential = dto.isEssential();
        this.maxSelect = dto.maxSelect();
    }

}
