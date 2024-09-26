package io.ssafy.p.j11a307.gateway.interceptor;

import io.ssafy.p.j11a307.gateway.exception.BusinessException;
import io.ssafy.p.j11a307.gateway.exception.ErrorCode;
import io.ssafy.p.j11a307.gateway.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtFilter implements WebFilter {

    private final String HEADER_AHTU = "Authorization";
    private final JwtUtil jwtUtil;
    private final FilterProperties filterProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();

        // 경로 필터링
        boolean shouldFilter = filterProperties.getIncludePaths().stream().anyMatch(path::startsWith)
                && filterProperties.getExcludePaths().stream().noneMatch(path::startsWith);

        if (shouldFilter) {
            String token = exchange.getRequest().getHeaders().getFirst(HEADER_AHTU);
            if (!isValidToken(token)) {
                throw new BusinessException(ErrorCode.INVALID_TOKEN);
            }

            if (jwtUtil.isExpired(token)) {
                throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
            }
        }

        return chain.filter(exchange);
    }

    private boolean isValidToken(String token) {
        return token != null && (token.startsWith("Bearer ") || jwtUtil.checkToken(token));
    }
}
