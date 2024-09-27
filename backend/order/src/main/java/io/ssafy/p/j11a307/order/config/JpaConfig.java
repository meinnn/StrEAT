package io.ssafy.p.j11a307.order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing //CreatedDate를 위해 활성화
public class JpaConfig {
}
