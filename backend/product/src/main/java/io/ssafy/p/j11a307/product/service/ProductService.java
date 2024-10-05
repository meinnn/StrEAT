package io.ssafy.p.j11a307.product.service;

import io.ssafy.p.j11a307.product.dto.CreateProductDTO;
import io.ssafy.p.j11a307.product.dto.ReadProductDTO;
import io.ssafy.p.j11a307.product.dto.UpdateProductDTO;
import io.ssafy.p.j11a307.product.entity.Product;
import io.ssafy.p.j11a307.product.exception.BusinessException;
import io.ssafy.p.j11a307.product.exception.ErrorCode;
import io.ssafy.p.j11a307.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public void createProduct(CreateProductDTO createProduct, String token) {
        Product product = Product.builder()
                .name(createProduct.name())
                .price(createProduct.price())
                .src(createProduct.src())
                .build();
        productRepository.save(product);
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

    @Transactional
    public void updateProduct(Integer productId, UpdateProductDTO UpdateProduct) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        Product updatedProduct = product.updateWith(UpdateProduct);

        productRepository.save(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

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
}