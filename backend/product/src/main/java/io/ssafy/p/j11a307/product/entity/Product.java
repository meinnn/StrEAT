package io.ssafy.p.j11a307.product.entity;

import io.ssafy.p.j11a307.product.dto.UpdateProductDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer storeId;
    private String name;
    private Integer price;
    private String description;
    @ColumnDefault("true")
    private Boolean stockStatus = true;

    @ManyToOne
    @JoinColumn(name = "product_category_id", nullable = false)
    private ProductCategory category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProductOptionCategory> optionCategories;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ProductPhoto> photos;

    // 이름 변경 메서드
    public void changeName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name;
    }

    // 가격 변경 메서드
    public void changePrice(Integer price) {
        if (price == null || price < 0) {
            throw new IllegalArgumentException("Price must be positive.");
        }
        this.price = price;
    }

    // 상품 설명 변경 메서드
    public void changeDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        this.description = description; // 수정된 부분
    }

    // 재고 상태 변경
    public void changeStockStatus(Boolean stockStatus) {
        if (stockStatus == null) {
            throw new IllegalArgumentException("Stock status cannot be null.");
        }
        this.stockStatus = stockStatus;
    }

    public void changeCategory(ProductCategory category) {
        if(category == null) {
            throw new IllegalArgumentException("Category cannot be null.");
        }
        this.category = category;
    }



    public Product updateWith(UpdateProductDTO request, ProductCategory category) {
        this.name = request.name() != null ? request.name() : this.name;
        this.price = request.price() != null ? request.price() : this.price;
        this.description = request.description() != null ? request.description() : this.description;
        this.stockStatus = request.stockStatus() != null ? request.stockStatus() : this.stockStatus;
        this.category = category != null ? category : this.category;

        return this; // 수정된 객체 반환
    }


}