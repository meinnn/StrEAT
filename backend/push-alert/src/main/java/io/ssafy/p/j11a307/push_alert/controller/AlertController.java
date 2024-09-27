package io.ssafy.p.j11a307.push_alert.controller;

import io.ssafy.p.j11a307.push_alert.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AlertController {

    private final AlertService alertService;
}
