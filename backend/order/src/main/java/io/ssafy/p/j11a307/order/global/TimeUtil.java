package io.ssafy.p.j11a307.order.global;

import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class TimeUtil {
    public LocalDateTime getCurrentSeoulTime() {
        return LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    // 주어진 시간을 LocalDateTime으로 변환 (시간대 정보 제거)
    public LocalDateTime convertToLocalDateTime(String seoulTimeString) {
        // 입력된 서울 시간 파싱 (2024-10-08T18:53:09+09:00)
        ZonedDateTime seoulTime = ZonedDateTime.parse(seoulTimeString);
        // ZonedDateTime에서 시간대 정보를 제거한 LocalDateTime 반환
        return seoulTime.toLocalDateTime();
    }
}
