package io.ssafy.p.j11a307.gateway.interceptor;

import io.ssafy.p.j11a307.gateway.exception.BusinessException;
import io.ssafy.p.j11a307.gateway.exception.ErrorCode;
import io.ssafy.p.j11a307.gateway.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class JwtFilter implements WebFilter {

    private final String HEADER_AHTU = "Authorization";
    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(HEADER_AHTU);

        if (token != null && !token.startsWith("Bearer ") && jwtUtil.checkToken(token)) {
            return chain.filter(exchange);
        }
        throw new BusinessException(ErrorCode.INVALID_TOKEN);
    }
}
