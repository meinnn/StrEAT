package io.ssafy.p.j11a307.product.entity;

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
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    private String name;

//    // 상위 카테고리를 참조하는 필드
//    @ManyToOne
//    @JoinColumn(name = "parent_category_id", nullable = true)
//    private ProductCategory parentCategory;

    // 카테고리명 변경 메서드
    public void changeName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name;
    }

    // 상위 카테고리를 계속해서 찾는 메서드 (재귀 호출)
//    public ProductCategory getRootCategory() {
//        if (this.parentCategory == null) {
//            return this;
//        }
//        return this.parentCategory.getRootCategory();
//    }

//    public void changeParentCategory(ProductCategory parentCategory) {
//        this.parentCategory = parentCategory;
//    }

//    public ProductCategory(Product product, UpdateProductCategoryDTO dto) {
//        this.product = product;
//        this.name = dto.name();
////        this.parentCategory = dto.parentCategoryId() != null ? new ProductCategory() : null; // 상위 카테고리 설정
//    }

}
