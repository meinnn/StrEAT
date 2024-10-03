package io.ssafy.p.j11a307.order.service;

import io.ssafy.p.j11a307.order.dto.ReadProductDTO;
import io.ssafy.p.j11a307.order.dto.ReadProductOptionCategoryDTO;
import io.ssafy.p.j11a307.order.dto.ReadProductOptionDTO;
import io.ssafy.p.j11a307.order.global.DataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@FeignClient(name = "PRODUCT")
public interface ProductClient {
    @GetMapping("/api/products/product-names")
    public DataResponse<List<String>> getProductNamesByProductIds(@RequestParam List<Integer> ids);

    @GetMapping("/api/products/{productId}")
    DataResponse<ReadProductDTO> getProductById(@PathVariable Integer productId);

    @GetMapping("/api/products/options/sum")
    Integer sumProductOption(@RequestParam List<Integer> optionList, @RequestHeader(value = "X-Internal-Request") String internalRequest);

    @GetMapping("/api/products/options/list")
    List<ReadProductOptionDTO> getProductOptionList(@RequestParam List<Integer> optionList, @RequestHeader(value = "X-Internal-Request") String internalRequest);

    @GetMapping("/api/products/options/{productId}/list")
    List<ReadProductOptionDTO> getProductOptionListByProductId(@PathVariable Integer productId, @RequestHeader(value = "X-Internal-Request") String internalRequest);

    @GetMapping("/api/products/option-categories/{optionCategoryId}")
    DataResponse<ReadProductOptionCategoryDTO> getProductOptionCategoryById(@PathVariable Integer optionCategoryId);

}