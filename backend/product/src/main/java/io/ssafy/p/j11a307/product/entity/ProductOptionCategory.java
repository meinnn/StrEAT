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

//    @ManyToOne
//    @JoinColumn(name = "parent_option_category_id")
//    private ProductOptionCategory parentCategory;

//    @OneToMany(mappedBy = "parentCategory") // 부모-자식 관계 설정
//    private List<ProductOptionCategory> subCategories;  // 자식 카테고리 리스트 추가

    @OneToMany(mappedBy = "productOptionCategory")
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

//     부모 카테고리 변경 메서드
//    public void changeParentCategory(ProductOptionCategory parentCategory) {
//
//    }
    public ProductOptionCategory(Product product, UpdateProductOptionCategoryDTO dto) {
        this.product = product;
        this.name = dto.name();
        this.isEssential = dto.isEssential();
        this.maxSelect = dto.maxSelect();
//        this.parentCategory = null;
    }
}
