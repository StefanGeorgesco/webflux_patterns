package fr.stefangeorgesco.webfluxpatterns.sec10_bulkhead.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("fibonacci")
public class FibonacciController {

    // CPU intensive operation
    @GetMapping("{input}")
    public Mono<ResponseEntity<Long>> getFibonacci(@PathVariable long input) {
        return Mono.fromSupplier(() -> fibonacci(input))
                .map(ResponseEntity::ok);
    }

    private long fibonacci(long n) {
        if (n < 2) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
