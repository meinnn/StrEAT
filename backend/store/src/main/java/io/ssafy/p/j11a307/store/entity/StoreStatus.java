package io.ssafy.p.j11a307.store.entity;

public enum StoreStatus {
    준비중,          // 영업 준비 중
    영업중,         // 정상 영업 중
    브레이크타임,   // 휴식 시간
    이동중,         // 다른 장소로 이동 중
    영업종료,       // 영업 중단 (일시적)
    폐업,           // 완전히 폐업
}