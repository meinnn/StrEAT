package io.ssafy.p.j11a307.product.service;

import io.ssafy.p.j11a307.product.dto.*;
import io.ssafy.p.j11a307.product.entity.Product;
import io.ssafy.p.j11a307.product.entity.ProductCategory;
import io.ssafy.p.j11a307.product.entity.ProductOptionCategory;
import io.ssafy.p.j11a307.product.exception.BusinessException;
import io.ssafy.p.j11a307.product.exception.ErrorCode;
import io.ssafy.p.j11a307.product.repository.ProductCategoryRepository;
import io.ssafy.p.j11a307.product.repository.ProductOptionCategoryRepository;
import io.ssafy.p.j11a307.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.ssafy.p.j11a307.product.entity.ProductPhoto;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final OwnerClient ownerClient;
    private final StoreClient storeClient;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductOptionCategoryRepository productOptionCategoryRepository;
    @Value("${streat.internal-request}")
    private String internalRequestKey;

    @Transactional
    public Integer createProduct(String token, CreateProductDTO createProductDTO) {
        Integer storeId = getStoreIdByToken(token);

        // 카테고리 조회
        ProductCategory category = productCategoryRepository.findById(createProductDTO.categoryId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND));

        // 상품 생성
        Product product = Product.builder()
                .storeId(storeId)
                .name(createProductDTO.name())
                .price(createProductDTO.price())
                .description(createProductDTO.description())
                .category(category)  // 카테고리 설정
                .stockStatus(true)
                .build();

        // 상품 저장
        productRepository.save(product);

        // 저장된 상품의 productId 반환
        return product.getId();
    }

    @Transactional(readOnly = true)
    public List<String> getProductCategoriesByStoreId(Integer storeId) {
        // storeId에 해당하는 카테고리 ID 목록을 중복 제거하여 가져옴
        List<Integer> categoryIds = productRepository.findDistinctCategoryIdsByStoreId(storeId);

        // 해당 ID 목록으로 카테고리 이름을 조회하여 반환
        return productRepository.findCategoryNamesByIds(categoryIds);
    }

    public ReadProductDTO getProductById(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
        return new ReadProductDTO(product);
    }

    public List<ReadProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ReadProductDTO::new)
                .collect(Collectors.toList());
    }

    public List<ReadProductDTO> getProductByStoreId(Integer storeId) {
        List<Product> products = productRepository.findByStoreId(storeId);
        return products.stream()
                .map(ReadProductDTO::new)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<ReadProductAllDTO> getProductsByStoreId(Integer storeId) {
        // storeId로 해당 가게의 모든 상품 조회
        List<Product> products = productRepository.findByStoreId(storeId);

        // 각 상품에 대해 ReadProductAllDTO를 생성하고 리스트로 반환
        return products.stream().map(product -> {
            // 옵션 카테고리 가져오기
            List<ProductOptionCategory> optionCategories = productOptionCategoryRepository.findByProductId(product.getId());

            // DTO 변환: ReadProductOptionCategoryDTO의 생성자를 활용
            List<ReadProductOptionCategoryDTO> optionCategoryDTOs = optionCategories.stream()
                    .map(ReadProductOptionCategoryDTO::new)
                    .toList();

            List<String> photoUrls = product.getPhotos().stream()
                    .map(ProductPhoto::getSrc) // 각 사진의 URL을 가져오는 로직
                    .toList();

            // 최종적으로 ReadProductAllDTO 생성
            return new ReadProductAllDTO(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getCategory().getId(),
                    product.getStockStatus(),
                    photoUrls,
                    optionCategoryDTOs
            );
        }).collect(Collectors.toList());
    }

    public void updateProduct(String token, Integer productId, UpdateProductDTO updateProductDTO) {
        Integer storeId = getStoreIdByToken(token);

        // storeId와 productId로 product 조회
        Optional<Product> product = productRepository.findByStoreIdAndId(storeId, productId);
        if (product.isPresent()) {
            // 카테고리 ID로 ProductCategory 조회
            ProductCategory productCategory = productCategoryRepository.findById(updateProductDTO.categoryId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND));

            // product와 category 정보를 이용하여 updateWith 메서드 호출
            Product updatedProduct = product.get().updateWith(updateProductDTO, productCategory);
            productRepository.save(updatedProduct);
        } else {
            throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);
        }
    }

    @Transactional
    public void updateProducts(String token, Integer productId, UpdateProductAllDTO productDTO) {
        // 1. 기존 상품 조회

        Integer storeId = getStoreIdByToken(token);

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        if(storeId != existingProduct.getStoreId()) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);
        }

        // 2. 상품 정보 업데이트
        if (productDTO.name() != null) {
            existingProduct.changeName(productDTO.name());
        }
        if (productDTO.description() != null) {
            existingProduct.changeDescription(productDTO.description());
        }
        if (productDTO.price() != null) {
            existingProduct.changePrice(productDTO.price());
        }

        // 3. 카테고리 업데이트
        if (productDTO.categoryId() != null) {
            ProductCategory category = productCategoryRepository.findById(productDTO.categoryId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_CATEGORY_NOT_FOUND));
            existingProduct.changeCategory(category);
        }

        // 4. 상품 정보 저장
        productRepository.save(existingProduct);
    }


    @Transactional
    public void deleteProduct(String token, Integer productId) {
        Integer storeId = getStoreIdByToken(token);

        // storeId와 productId로 product 조회
        Product product = productRepository.findByStoreIdAndId(storeId, productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        // Product 삭제
        productRepository.delete(product);
    }

    @Transactional(readOnly = true)
    public List<String> getProductNamesByProductIds(List<Integer> productIds) {
        return productIds.stream()
                .map(id -> productRepository.findById(id)
                        .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND, "=> 해당하지 않는 상품 ID: " + id)))
                .map(Product::getName)
                .collect(Collectors.toList());
    }

    // 공통된 userId와 storeId 조회 로직을 메서드로 추출
    private Integer getStoreIdByToken(String token) {
        // token을 통해 userId 조회
        Integer userId = ownerClient.getUserId(token, internalRequestKey);
        if (userId == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND); // 유저가 없을 때 예외 처리
        }

        // userId로 storeId 조회 (StoreClient 사용)
        Integer storeId = storeClient.getStoreIdByUserId(userId);
        if (storeId == null) {
            throw new BusinessException(ErrorCode.STORE_NOT_FOUND); // storeId를 찾을 수 없을 때 예외 처리
        }

        return storeId;
    }

    @Transactional
    public boolean toggleProductStockStatus(Integer productId, String token) {
        // 1. 공통 메서드로 Token을 통해 사용자의 storeId 가져오기
        Integer storeId = getStoreIdByToken(token);

        // 2. Product가 해당 store의 상품인지 확인
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        if (!product.getStoreId().equals(storeId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_USER); // 다른 가게 상품에 접근하는 것을 방지
        }

        // 3. 현재 재고 상태를 반대로 변경 (true -> false, false -> true)
        Boolean currentStockStatus = product.getStockStatus();
        product.changeStockStatus(!currentStockStatus);

        // 4. 변경된 정보를 저장
        productRepository.save(product);
        return !currentStockStatus;
    }

}