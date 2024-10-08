package io.ssafy.p.j11a307.store.service;

import io.ssafy.p.j11a307.store.dto.SubCategoryDTO;
import io.ssafy.p.j11a307.store.dto.TopCategoryWithSubCategoriesDTO;
import io.ssafy.p.j11a307.store.entity.TopCategory;
import io.ssafy.p.j11a307.store.repository.TopCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final TopCategoryRepository topCategoryRepository;

    public CategoryService(TopCategoryRepository topCategoryRepository) {
        this.topCategoryRepository = topCategoryRepository;
    }

    @Transactional(readOnly = true)
    public List<TopCategoryWithSubCategoriesDTO> getAllTopCategoriesWithSubCategories() {
        List<TopCategory> topCategories = topCategoryRepository.findAll();

        return topCategories.stream()
                .map(topCategory -> new TopCategoryWithSubCategoriesDTO(
                        topCategory.getId(),
                        topCategory.getName(),
                        topCategory.getSubCategories().stream()
                                .map(subCategory -> new SubCategoryDTO(subCategory.getId(), subCategory.getName()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

}
