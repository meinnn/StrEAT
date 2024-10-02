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

    @Transactional
    public void createProductOption(CreateProductOptionDTO createProductOption) {
        Product product = productRepository.findById(createProductOption.productId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

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
    public void updateProductOption(Integer productOptionId, UpdateProductOptionDTO updateProductOption) {
        ProductOption productOption = productOptionRepository.findById(productOptionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_OPTION_NOT_FOUND));

        Product product = productRepository.findById(updateProductOption.productId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        ProductOptionCategory productOptionCategory = productOptionCategoryRepository.findById(updateProductOption.productOptionCategoryId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_OPTION_CATEGORY_NOT_FOUND));

        // Update product option with the new data
        updateProductOption.updateEntity(productOption, product, productOptionCategory);
        productOptionRepository.save(productOption);
    }

    @Transactional
    public void deleteProductOption(Integer productOptionId) {
        ProductOption productOption = productOptionRepository.findById(productOptionId)
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
}