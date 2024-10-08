package io.ssafy.p.j11a307.announcement.global;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum StoreType {
    MOBILE("이동형"),      // 이동형 점포
    FIXED("고정형");       // 고정형 점포

    private final String description;

    StoreType(String description) {
        this.description = description;
    }

    // type 문자열을 enum으로 변환
    public static StoreType fromDescription(String type) {
        for (StoreType storeType : StoreType.values()) {
            if (storeType.getDescription().equals(type)) {
                return storeType;
            }
        }
        throw new IllegalArgumentException("Invalid store type: " + type);
    }

    // JSON 직렬화/역직렬화를 위한 메서드
    @JsonValue
    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static StoreType from(String value) {
        return fromDescription(value);
    }
}
