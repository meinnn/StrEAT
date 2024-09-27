package io.ssafy.p.j11a307.product.service;

import io.ssafy.p.j11a307.product.dto.ProductResponse;
import io.ssafy.p.j11a307.product.dto.ProductUpdateRequest;
import io.ssafy.p.j11a307.product.entity.Product;
import io.ssafy.p.j11a307.product.exception.BusinessException;
import io.ssafy.p.j11a307.product.exception.ErrorCode;
import io.ssafy.p.j11a307.product.repository.ProductRepository;

import io.ssafy.p.j11a307.product.service.OwnerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService{

    private final ProductRepository productRepository;
    private final OwnerClient ownerClient;

    @Value("{streat.internal-request}")
    private String internalRequestKey;

    public Integer getOwnerIdByProductId(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
        return product.getUserId();
    }

    public ProductResponse getProductInfo(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        return new ProductResponse(product);
    }

    public ProductResponse createProduct(ProductUpdateRequest request, String token) {
        // JWT 토큰에서 userID 추출
        Integer userId = ownerClient.getUserId(token, internalRequestKey);

        if (userId == null) {
            // 조회된 owner 정보가 없으면 예외 발생
            throw new BusinessException(ErrorCode.OWNER_NOT_FOUND);
        }

        Product product = Product.builder()
                .name(request.name())
                .address(request.address())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .type(request.type())
                .bankAccount(request.bankAccount())
                .bankName(request.bankName())
                .ownerWord(request.ownerWord())
                .status(request.status())
                .userId(userId)
                .build();

        Product savedproduct = productRepository.save(product);
        return new ProductResponse(savedproduct);
    }

    public String getProductType(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
        return product.getType();
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::new)  // product -> productResponse 변환
                .collect(Collectors.toList());
    }


    public ProductResponse updateProduct(Integer id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        Product updatedProduct = product.updateWith(request);

        return new ProductResponse(productRepository.save(updatedProduct));
    }

    public void deleteProduct(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        productRepository.delete(product);
    }

    public ProductResponse  updateProductAddress(Integer id, String newAddress) {
        Product product = productRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
        product.changeAddress(newAddress);
        return new ProductResponse(productRepository.save(product));
    }
}
