package io.ssafy.p.j11a307.product.entity;

import io.ssafy.p.j11a307.product.dto.UpdateProductDTO;
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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer storeId;
    private String name;
    private Integer price;
    private String src;

    @OneToMany(mappedBy = "product")
    private List<ProductCategory> categories;

    @OneToMany(mappedBy = "product")
    private List<ProductOptionCategory> optionCategories;

    // 이름 변경 메서드
    public void changeName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name;
    }

    // src 변경 메서드
    public void changeSrc(String src) {
        if (src == null || src.isEmpty()) {
            throw new IllegalArgumentException("Src cannot be empty.");
        }
        this.src = src;
    }

    // 가격 변경 메서드
    public void changePrice(Integer price) {
        if (price == null || price < 0) {
            throw new IllegalArgumentException("Price must be positive.");
        }
        this.price = price;
    }

    // 업데이트 메서드
    public Product updateWith(UpdateProductDTO request) {
        return Product.builder()
                .id(this.id)  // ID는 변경하지 않음
                .storeId(this.storeId)  // storeId는 변경하지 않음
                .name(request.name() != null ? request.name() : this.name)
                .price(request.price() != null ? request.price() : this.price)
                .src(request.src() != null ? request.src() : this.src)
                .build();
    }
}