package io.ssafy.p.j11a307.store.global;

import io.ssafy.p.j11a307.store.entity.StoreStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToStoreStatusConverter implements Converter<String, StoreStatus> {

    @Override
    public StoreStatus convert(String source) {
        return StoreStatus.fromDescription(source);  // description으로 변환
    }
}