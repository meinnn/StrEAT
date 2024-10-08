package io.ssafy.p.j11a307.announcement.global;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StoreStatus {
    READY("준비중"),         // 영업 준비 중
    OPEN("영업중"),          // 정상 영업 중
    BREAK_TIME("브레이크타임"),  // 휴식 시간
    MOVING("이동중"),         // 다른 장소로 이동 중
    TEMP_CLOSED("영업종료"),   // 영업 중단 (일시적)
    CLOSED("폐업");          // 완전히 폐업

    private final String description;

    StoreStatus(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static StoreStatus fromDescription(String description) {
        for (StoreStatus status : StoreStatus.values()) {
            if (status.description.equals(description)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No enum constant with description " + description);
    }
}
