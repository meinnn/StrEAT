package io.ssafy.p.j11a307.push_alert.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FcmNotification {

    private final String title;
    private final String body;
}
