package io.ssafy.p.j11a307.store.controller;

import io.ssafy.p.j11a307.store.dto.TopCategoryWithSubCategoriesDTO;
import io.ssafy.p.j11a307.store.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    @Operation(summary = "모든 TopCategory와 해당하는 SubCategory 목록 조회")
    public ResponseEntity<List<TopCategoryWithSubCategoriesDTO>> getAllTopCategoriesWithSubCategories() {
        List<TopCategoryWithSubCategoriesDTO> categories = categoryService.getAllTopCategoriesWithSubCategories();
        return ResponseEntity.ok(categories);
    }
}