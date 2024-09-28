package io.ssafy.p.j11a307.push_alert.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FcmAlert {

    @Builder.Default
    private Boolean validateOnly = false;
    private FcmMessage message;
}
