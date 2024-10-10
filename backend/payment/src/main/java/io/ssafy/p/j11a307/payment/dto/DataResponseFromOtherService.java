package io.ssafy.p.j11a307.payment.dto;

import lombok.Getter;

@Getter
public class DataResponseFromOtherService<T> {

    private T data;
    private String message;
}
