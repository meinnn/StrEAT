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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final OwnerClient ownerClient;
    private final StoreClient storeClient;  // StoreClient를 사용하여 storeId 조회

    @Value("${streat.internal-request}")
    private String internalRequestKey;

    // 1. 상품 카테고리 생성
    @Transactional
    public void createProductCategory(String token, CreateProductCategoryDTO createCategoryDTO) {
        // token으로 userId 조회
        Integer storeId = getStoreIdByToken(token);

        // storeId로 product 조회
        List<Product> products = productRepository.findByStoreId(storeId);
        if (products.isEmpty()) {
            throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        Product product = products.get(0);  // 첫 번째 product 사용

        // 상위 카테고리 조회 (없을 수도 있음)
//        ProductCategory parentCategory = getParentCategory(createCategoryDTO.parentCategoryId());

        // DTO를 엔티티로 변환하여 저장
        ProductCategory productCategory = createCategoryDTO.toEntity(product);
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
    public void updateProductCategory(String token, Integer categoryId, UpdateProductCategoryDTO updateCategoryDTO) {
        Integer storeId = getStoreIdByToken(token);

        // storeId로 product 조회
        List<Product> products = productRepository.findByStoreId(storeId);
        if (products.isEmpty()) {
            throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        Product product = products.get(0);  // 첫 번째 product 사용

        // 기존 카테고리 조회
        ProductCategory category = productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND));

        // DTO를 사용하여 카테고리 업데이트
        updateCategoryDTO.updateEntity(category); // 부모 카테고리가 null일 경우를 처리

        // 변경된 카테고리 저장
        productCategoryRepository.save(category);
    }

    // 5. 상품 카테고리 삭제
    @Transactional
    public void deleteProductCategory(String token, Integer categoryId) {
        Integer storeId = getStoreIdByToken(token);

        // storeId로 product 조회
        List<Product> products = productRepository.findByStoreId(storeId);
        if (products.isEmpty()) {
            throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        Product product = products.get(0);  // 첫 번째 product 사용

        // 기존 카테고리 조회
        ProductCategory category = productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND));

        productCategoryRepository.delete(category);
    }


    // 공통으로 사용하는 메서드들
    private Integer getStoreIdByToken(String token) {
        // token으로 userId 조회
        Integer userId = ownerClient.getUserId(token, internalRequestKey);
        if (userId == null) {
            throw new BusinessException(ErrorCode.OWNER_NOT_FOUND);
        }

        // userId로 storeId 조회 (StoreClient 사용)
        Integer storeId = storeClient.getStoreIdByUserId(userId);
        if (storeId == null) {
            throw new BusinessException(ErrorCode.STORE_NOT_FOUND);
        }

        return storeId;
    }


//    private ProductCategory getParentCategory(Integer parentCategoryId) {
//        if (parentCategoryId == null) {
//            return null;
//        }
//
//        return productCategoryRepository.findById(parentCategoryId)
//                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND));
//    }
}