package io.ssafy.p.j11a307.product.service;

import io.ssafy.p.j11a307.product.dto.CreateProductDTO;
import io.ssafy.p.j11a307.product.dto.ReadProductDTO;
import io.ssafy.p.j11a307.product.dto.UpdateProductDTO;
import io.ssafy.p.j11a307.product.entity.Product;
import io.ssafy.p.j11a307.product.exception.BusinessException;
import io.ssafy.p.j11a307.product.exception.ErrorCode;
import io.ssafy.p.j11a307.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final OwnerClient ownerClient;
    private final StoreClient storeClient;

    @Value("${streat.internal-request}")
    private String internalRequestKey;

    @Transactional
    public Integer createProduct(String token, CreateProductDTO createProduct) {
        Integer storeId = getStoreIdByToken(token);

        // 상품 생성 (CreateProductDTO에서 정보 가져오기)
        Product product = Product.builder()
                .storeId(storeId) // storeId 설정
                .name(createProduct.name()) // 상품 이름 설정
                .price(createProduct.price()) // 상품 가격 설정
                .description(createProduct.description()) // 상품 설명 설정 (필요한 경우 추가)
                .build();

        // 상품 저장
        productRepository.save(product);

        // 저장된 상품의 productId 반환
        return product.getId();
    }

    @Transactional(readOnly = true)
    public List<String> getProductCategoriesByStoreId(Integer storeId) {
        // storeId에 해당하는 카테고리 이름만 리스트로 반환
        return productRepository.findCategoriesByStoreId(storeId);
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

    public List<ReadProductDTO> getProductsByStoreId(Integer storeId) {
        List<Product> products = productRepository.findByStoreId(storeId);
        return products.stream()
                .map(ReadProductDTO::new)
                .collect(Collectors.toList());
    }

    public void updateProduct(String token, Integer productId, UpdateProductDTO updateProductDTO) {
        Integer storeId = getStoreIdByToken(token);

        Optional<Product> product = productRepository.findByStoreIdAndId(storeId, productId);
        // storeId와 productId로 product 조회

        if(product.isPresent()){
            Product updatedProduct = product.get().updateWith(updateProductDTO);
            productRepository.save(updatedProduct);
        }
        else throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);

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
    public void toggleProductStockStatus(Integer productId, String token) {
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
    }


    @Transactional(readOnly = true)
    public Integer getLastProductId() {
        return productRepository.findLastProductId();
    }
}