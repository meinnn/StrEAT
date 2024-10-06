package io.ssafy.p.j11a307.store.service;

import io.ssafy.p.j11a307.store.global.DataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "PRODUCT")
public interface ProductClient {
    @GetMapping("/api/products/{storeId}/categories")
    DataResponse<List<String>> getProductCategories(@PathVariable Integer storeId);
}