package io.ssafy.p.j11a307.product.service;

import io.ssafy.p.j11a307.product.dto.CreateProductOptionCategoryDTO;
import io.ssafy.p.j11a307.product.dto.ReadProductOptionCategoryDTO;
import io.ssafy.p.j11a307.product.dto.UpdateProductOptionCategoryDTO;
import io.ssafy.p.j11a307.product.entity.Product;
import io.ssafy.p.j11a307.product.entity.ProductOptionCategory;
import io.ssafy.p.j11a307.product.exception.BusinessException;
import io.ssafy.p.j11a307.product.exception.ErrorCode;
import io.ssafy.p.j11a307.product.repository.ProductOptionCategoryRepository;
import io.ssafy.p.j11a307.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductOptionCategoryService {

    private final ProductOptionCategoryRepository productOptionCategoryRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Integer createProductOptionCategory(CreateProductOptionCategoryDTO createOptionCategoryDTO) {
        // 1. productId로 해당 Product를 조회
        Product product = productRepository.findById(createOptionCategoryDTO.productId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        // 2. DTO를 엔티티로 변환하여 저장
        ProductOptionCategory optionCategory = createOptionCategoryDTO.toEntity(product);
        ProductOptionCategory savedOptionCategory = productOptionCategoryRepository.save(optionCategory);

        // 3. 저장된 ProductOptionCategory의 ID 반환
        return savedOptionCategory.getId();
    }

    // 2. 옵션 카테고리 ID로 조회
    @Transactional(readOnly = true)
    public ReadProductOptionCategoryDTO getProductOptionCategoryById(Integer optionCategoryId) {
        ProductOptionCategory optionCategory = productOptionCategoryRepository.findById(optionCategoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_OPTION_CATEGORY_NOT_FOUND));

        return new ReadProductOptionCategoryDTO(optionCategory);
    }

    // 3. 모든 옵션 카테고리 조회
    @Transactional(readOnly = true)
    public List<ReadProductOptionCategoryDTO> getAllProductOptionCategories() {
        return productOptionCategoryRepository.findAll().stream()
                .map(ReadProductOptionCategoryDTO::new)
                .collect(Collectors.toList());
    }

    // 4. 옵션 카테고리 수정
    @Transactional
    public void updateProductOptionCategory(Integer optionCategoryId, UpdateProductOptionCategoryDTO updateOptionCategoryDTO) {
        // 기존 옵션 카테고리 조회
        ProductOptionCategory optionCategory = productOptionCategoryRepository.findById(optionCategoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_OPTION_CATEGORY_NOT_FOUND));

        // 상위 카테고리 조회 (없을 수도 있음)
//        ProductOptionCategory parentCategory = null;
//        if (updateOptionCategoryDTO.parentOptionCategoryId() != null) {
//            parentCategory = productOptionCategoryRepository.findById(updateOptionCategoryDTO.parentOptionCategoryId())
//                    .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_OPTION_CATEGORY_NOT_FOUND));
//        }

        // DTO를 사용하여 옵션 카테고리 업데이트
        updateOptionCategoryDTO.updateEntity(optionCategory);

        // 변경된 옵션 카테고리 저장
        productOptionCategoryRepository.save(optionCategory);
    }

    // 5. 옵션 카테고리 삭제
    @Transactional
    public void deleteProductOptionCategory(Integer optionCategoryId) {
        ProductOptionCategory optionCategory = productOptionCategoryRepository.findById(optionCategoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_OPTION_CATEGORY_NOT_FOUND));

        productOptionCategoryRepository.delete(optionCategory);
    }

    public Integer getLastProductOptionCategoryId() {
        Integer lastId = productOptionCategoryRepository.findLastProductOptionCategoryId();
        return (lastId != null) ? lastId : 0;
    }
}
