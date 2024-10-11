package io.ssafy.p.j11a307.product.service;

import io.ssafy.p.j11a307.product.dto.CreateProductOptionDTO;
import io.ssafy.p.j11a307.product.dto.ReadProductOptionDTO;
import io.ssafy.p.j11a307.product.dto.UpdateProductOptionDTO;
import io.ssafy.p.j11a307.product.entity.Product;
import io.ssafy.p.j11a307.product.entity.ProductOption;
import io.ssafy.p.j11a307.product.entity.ProductOptionCategory;
import io.ssafy.p.j11a307.product.exception.BusinessException;
import io.ssafy.p.j11a307.product.exception.ErrorCode;
import io.ssafy.p.j11a307.product.repository.ProductOptionRepository;
import io.ssafy.p.j11a307.product.repository.ProductRepository;
import io.ssafy.p.j11a307.product.repository.ProductOptionCategoryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductOptionService {

    private final ProductOptionRepository productOptionRepository;
    private final ProductRepository productRepository;
    private final ProductOptionCategoryRepository productOptionCategoryRepository;
    private final StoreClient storeClient;
    private final OwnerClient ownerClient;

    @Value("${streat.internal-request}")
    private String internalRequestKey;

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
    public void createProductOption(String token, CreateProductOptionDTO createProductOption) {
        Integer storeId = getStoreIdByToken(token);

        Product product = productRepository.findById(createProductOption.productId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        if (!product.getStoreId().equals(storeId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);
        }

        ProductOptionCategory productOptionCategory = productOptionCategoryRepository.findById(createProductOption.productOptionCategoryId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_OPTION_CATEGORY_NOT_FOUND));

        ProductOption productOption = createProductOption.toEntity(product, productOptionCategory);
        productOptionRepository.save(productOption);
    }

    public ReadProductOptionDTO getProductOptionById(Integer productOptionId) {
        ProductOption productOption = productOptionRepository.findById(productOptionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_OPTION_NOT_FOUND));
        return new ReadProductOptionDTO(productOption);
    }

    public List<ReadProductOptionDTO> getAllProductOptions() {
        List<ProductOption> productOptions = productOptionRepository.findAll();
        return productOptions.stream()
                .map(ReadProductOptionDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateProductOption(String token, Integer productOptionId, UpdateProductOptionDTO updateProductOption) {
        Integer storeId = getStoreIdByToken(token);

        ProductOption productOption = productOptionRepository.findById(productOptionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_OPTION_NOT_FOUND));

        Product product = productRepository.findById(updateProductOption.productId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        if (!product.getStoreId().equals(storeId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);
        }

        ProductOptionCategory productOptionCategory = productOptionCategoryRepository.findById(updateProductOption.productOptionCategoryId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_OPTION_CATEGORY_NOT_FOUND));

        // Update product option with the new data
        updateProductOption.updateEntity(productOption, product, productOptionCategory);
        productOptionRepository.save(productOption);
    }

    @Transactional
    public void deleteProductOption(String token, Integer productOptionId) {
        Integer storeId = getStoreIdByToken(token);
        ProductOption productOption = productOptionRepository.findById(productOptionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_OPTION_NOT_FOUND));

        if (!productOption.getProduct().getStoreId().equals(storeId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_USER);
        }

        productOptionRepository.delete(productOption);
    }

    @Transactional
    public void deleteProductOptions(Integer optionId) {
        ProductOption productOption = productOptionRepository.findById(optionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_OPTION_NOT_FOUND));
        productOptionRepository.delete(productOption);
    }

    @Transactional
    public Integer sumProductOption(List<Integer> optionList) {
        int sum = 0;

        for (Integer optionId : optionList) {
            //해당 옵션이 겨냥한 상품에 대한 옵션이 아니면 에러
            Optional<ProductOption> productOption = productOptionRepository.findById(optionId);

            if(productOption.isPresent()) sum += productOption.get().getProductOptionPrice();
            else throw new BusinessException(ErrorCode.PRODUCT_OPTION_NOT_FOUND);
        }

        return sum;
    }

    @Transactional
    public List<ReadProductOptionDTO> getProductOptionList(List<Integer> optionList) {
        List<ReadProductOptionDTO> readProductOptionDTOs = new ArrayList<>();

        for(Integer optionId : optionList) {
            //해당 옵션이 겨냥한 상품에 대한 옵션이 아니면 에러
            Optional<ProductOption> productOption = productOptionRepository.findById(optionId);

            if(productOption.isPresent()) {
                ReadProductOptionDTO readProductOptionDTO = new ReadProductOptionDTO(productOption.get());
                readProductOptionDTOs.add(readProductOptionDTO);
            }
            else throw new BusinessException(ErrorCode.PRODUCT_OPTION_NOT_FOUND);

        }

        return readProductOptionDTOs;
    }

    @Transactional
    public List<ReadProductOptionDTO> getProductOptionListByProductId(Integer productId) {
        Optional<Product> product = productRepository.findById(productId);
        List<ReadProductOptionDTO> readProductOptionDTOs = new ArrayList<>();

        if(product.isPresent()) readProductOptionDTOs = productOptionRepository.findAllByProduct(product.get());

        return readProductOptionDTOs;
    }

    public List<ProductOption> findByProductOptionCategoryId(Integer productOptionCategoryId) {
        return productOptionRepository.findByProductOptionCategoryId(productOptionCategoryId);
    }


    @Transactional
    public Integer updateOrCreateProductOption(String token, Integer productId, Integer productOptionCategoryId, UpdateProductOptionDTO optionDTO) {
        // 1. 상품 및 옵션 카테고리 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
        ProductOptionCategory productOptionCategory = productOptionCategoryRepository.findById(productOptionCategoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_OPTION_CATEGORY_NOT_FOUND));

        // 2. 옵션 조회 (기존 옵션이 있는지 확인)
        ProductOption existingOption = productOptionRepository.findByProductOptionCategoryIdAndProductOptionName(
                        productOptionCategoryId, optionDTO.productOptionName())
                .orElseGet(() -> ProductOption.builder()
                        .product(product)
                        .productOptionCategory(productOptionCategory)
                        .productOptionName(optionDTO.productOptionName())
                        .productOptionPrice(optionDTO.productOptionPrice())
                        .build());

        // 3. 옵션 업데이트
        if (existingOption.getId() != null) {
            existingOption.updateOptionName(optionDTO.productOptionName());
            existingOption.updateOptionPrice(optionDTO.productOptionPrice());
        } else {
            // 새로운 옵션인 경우 저장
            existingOption = productOptionRepository.save(existingOption);
        }

        return existingOption.getId();
    }
}