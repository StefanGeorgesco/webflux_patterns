package fr.stefangeorgesco.webfluxpatterns.sec10_bulkhead.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("fibonacci")
public class CalculatorController {

    private static final Logger log = LoggerFactory.getLogger(CalculatorController.class);

    // CPU intensive operation
    // 5 requests / 20 seconds
    @GetMapping("{input}")
    @RateLimiter(name = "calculator-service", fallbackMethod = "fallback")
    public Mono<ResponseEntity<Integer>> doubleInput(@PathVariable int input) {
        return Mono.fromSupplier(() -> input * 2)
                .map(ResponseEntity::ok);
    }

    @SuppressWarnings("unused")
    public Mono<ResponseEntity<String>> fallback(int input, Throwable t) {
        if (log.isWarnEnabled()) {
            log.warn("Rate limit exceeded for input: {} because of: {}", input, t.getMessage());
        }
        return Mono.just(ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Rate limit exceeded"));
    }
}
