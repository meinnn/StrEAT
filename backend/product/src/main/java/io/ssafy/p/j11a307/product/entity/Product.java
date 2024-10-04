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
    private String description;

    @OneToMany(mappedBy = "product")
    private List<ProductCategory> categories;

    @OneToMany(mappedBy = "product")
    private List<ProductOptionCategory> optionCategories;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public Product updateWith(UpdateProductDTO request) {
        this.name = request.name() != null ? request.name() : this.name;
        this.price = request.price() != null ? request.price() : this.price;
        this.description = request.description() != null ? request.description() : this.description; // 설명 필드 추가

        // 카테고리 및 옵션 카테고리 업데이트
        if (request.categories() != null) {
            this.categories.clear(); // 기존 카테고리 삭제
            this.categories.addAll(request.categories().stream()
                    .map(categoryDto -> new ProductCategory(this, categoryDto))
                    .toList());
        }

        if (request.optionCategories() != null) {
            this.optionCategories.clear(); // 기존 옵션 카테고리 삭제
            this.optionCategories.addAll(request.optionCategories().stream()
                    .map(optionCategoryDto -> new ProductOptionCategory(this, optionCategoryDto))
                    .toList());
        }

        return this; // 수정된 객체 반환
    }
}