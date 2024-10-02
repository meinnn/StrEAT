package io.ssafy.p.j11a307.order.service;

import io.ssafy.p.j11a307.order.global.DataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@FeignClient(name = "PRODUCT")
public interface ProductClient {
    @GetMapping("/api/products/product-names")
    public DataResponse<List<String>> getProductNamesByProductIds(@RequestParam List<Integer> ids);
}
