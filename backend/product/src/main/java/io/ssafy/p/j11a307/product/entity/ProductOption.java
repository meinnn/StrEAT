package io.ssafy.p.j11a307.product.entity;

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
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Product와의 Many-to-One 관계 설정
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "product_option_category_id")
    private ProductOptionCategory productOptionCategory;

    private String description;

    // 설명 변경 메서드
    public void changeDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        this.description = description;
    }

    // 옵션 카테고리 변경 메서드
    public void changeOptionCategory(ProductOptionCategory productOptionCategory) {
        if (productOptionCategory == null) {
            throw new IllegalArgumentException("Product option category cannot be null.");
        }
        this.productOptionCategory = productOptionCategory;
    }
}