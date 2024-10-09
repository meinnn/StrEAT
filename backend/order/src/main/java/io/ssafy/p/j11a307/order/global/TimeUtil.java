package io.ssafy.p.j11a307.order.global;

import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Configuration
public class TimeUtil {
    public LocalDateTime getCurrentSeoulTime() {
        return LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }
}
