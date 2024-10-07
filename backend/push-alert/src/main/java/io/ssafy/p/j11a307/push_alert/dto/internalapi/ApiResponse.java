package io.ssafy.p.j11a307.push_alert.dto.internalapi;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {
    private String message;
    private T data;
}
