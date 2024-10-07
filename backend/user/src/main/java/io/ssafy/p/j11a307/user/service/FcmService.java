package io.ssafy.p.j11a307.user.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "push-alert")
public interface FcmService {

    @PostMapping("/api/push-alert/dibs/{storeId}")
    void subscribeStore(@PathVariable Integer storeId,
                        @RequestHeader(value = "X-Internal-Request") String internalRequest,
                        @RequestParam Integer userId);

    @DeleteMapping("/api/push-alert/dibs/{storeId}")
    void unsubscribeStore(@PathVariable Integer storeId,
                          @RequestHeader(value = "X-Internal-Request") String internalRequest,
                          @RequestParam Integer userId);
}
