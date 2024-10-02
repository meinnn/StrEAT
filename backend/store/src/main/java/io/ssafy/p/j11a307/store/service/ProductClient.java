package io.ssafy.p.j11a307.store.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "product", url = "${product-service.url}")
public interface ProductClient {
    @GetMapping("/api/products/{storeId}/categories")
    List<String> getProductCategories(@PathVariable Integer storeId);
}