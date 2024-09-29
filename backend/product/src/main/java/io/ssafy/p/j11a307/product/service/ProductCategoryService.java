package io.ssafy.p.j11a307.product.service;

import io.ssafy.p.j11a307.product.dto.CreateProductCategoryDTO;
import io.ssafy.p.j11a307.product.dto.ReadProductCategoryDTO;
import io.ssafy.p.j11a307.product.dto.UpdateProductCategoryDTO;
import io.ssafy.p.j11a307.product.entity.Product;
import io.ssafy.p.j11a307.product.entity.ProductCategory;
import io.ssafy.p.j11a307.product.exception.BusinessException;
import io.ssafy.p.j11a307.product.exception.ErrorCode;
import io.ssafy.p.j11a307.product.repository.ProductCategoryRepository;
import io.ssafy.p.j11a307.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;

    // 1. 상품 카테고리 생성
    @Transactional
    public void createProductCategory(CreateProductCategoryDTO createCategoryDTO) {
        Product product = productRepository.findById(createCategoryDTO.productId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        // 상위 카테고리 조회 (없을 수도 있음)
        ProductCategory parentCategory = null;
        if (createCategoryDTO.parentCategoryId() != null) {
            parentCategory = productCategoryRepository.findById(createCategoryDTO.parentCategoryId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND));
        }

        // DTO를 엔티티로 변환하여 저장
        ProductCategory productCategory = createCategoryDTO.toEntity(product, parentCategory);
        productCategoryRepository.save(productCategory);
    }

    // 2. 상품 카테고리 ID로 조회
    @Transactional(readOnly = true)
    public ReadProductCategoryDTO getProductCategoryById(Integer categoryId) {
        ProductCategory category = productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND));

        return new ReadProductCategoryDTO(category);
    }

    // 3. 모든 상품 카테고리 조회
    @Transactional(readOnly = true)
    public List<ReadProductCategoryDTO> getAllProductCategories() {
        return productCategoryRepository.findAll().stream()
                .map(ReadProductCategoryDTO::new)
                .collect(Collectors.toList());
    }

    // 4. 상품 카테고리 수정
    @Transactional
    public void updateProductCategory(Integer categoryId, UpdateProductCategoryDTO updateCategoryDTO) {
        // 기존 카테고리 조회
        ProductCategory category = productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND));

        // DTO를 사용하여 카테고리 업데이트
        updateCategoryDTO.updateEntity(category);

        // 변경된 카테고리 저장
        productCategoryRepository.save(category);
    }

    // 5. 상품 카테고리 삭제
    @Transactional
    public void deleteProductCategory(Integer categoryId) {
        ProductCategory category = productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND));

        productCategoryRepository.delete(category);
    }
}