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

    private String productOptionName;
    private Integer productOptionPrice;

    // 옵션 카테고리 변경 메서드
    public void changeOptionCategory(ProductOptionCategory productOptionCategory) {
        if (productOptionCategory == null) {
            throw new IllegalArgumentException("Product option category cannot be null.");
        }
        this.productOptionCategory = productOptionCategory;
    }

    //옵션 이름 변경 메서드
    public void changeProductOptionName(ProductOption productOption) {
        if (productOption == null) {
            throw new IllegalArgumentException("Product option cannot be null.");
        }
        this.productOptionName = productOption.getProductOptionName();
    }

    //옵션 가격 변경 메서드
    public void changeProductOptionPrice(ProductOption productOption) {
        if (productOption == null) {
            throw new IllegalArgumentException("Product option cannot be null.");
        }
        this.productOptionPrice = productOption.getProductOptionPrice();
    }


}