package com.encuentros.gateway.fallback;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @GetMapping(value = "/fallback", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Mono<String> fallback() {
        return Mono.just("{\"message\":\"Servicio no disponible, intenta más tarde\"}");
    }
}
