package com.encuentros.gateway.filters;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.UUID;

@Configuration
public class CorrelationFilter {

    public static final String HEADER = "X-Correlation-Id";

    @Bean
    public GlobalFilter addCorrelationId() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (!request.getHeaders().containsKey(HEADER)) {
                String id = UUID.randomUUID().toString();
                request = request.mutate().header(HEADER, id).build();
                return chain.filter(exchange.mutate().request(request).build());
            }
            return chain.filter(exchange);
        };
    }
}
