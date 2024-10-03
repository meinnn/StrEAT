package io.ssafy.p.j11a307.order.controller;

import io.ssafy.p.j11a307.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class OrderController {
    private final OrderService orderService;



}
