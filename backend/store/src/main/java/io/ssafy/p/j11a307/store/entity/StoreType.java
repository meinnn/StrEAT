package io.ssafy.p.j11a307.store.entity;

public enum StoreType {
    MOBILE("이동형"),      // 이동형 점포
    FIXED("고정형");       // 고정형 점포

    private final String description;

    StoreType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static StoreType fromDescription(String description) {
        for (StoreType type : StoreType.values()) {
            if (type.description.equals(description)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with description " + description);
    }
}
