package fr.stefangeorgesco.webfluxpatterns.sec10_bulkhead.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("fibonacci")
public class FibonacciController {

    private final Scheduler scheduler = Schedulers.newParallel("fibonacci-scheduler", 6);

    // CPU intensive operation
    @GetMapping("{input}")
    public Mono<ResponseEntity<Long>> getFibonacci(@PathVariable long input) {
        return Mono.fromSupplier(() -> fibonacci(input))
                // limit the number of concurrent threads to 6, to spare CPU resources for other services
                .subscribeOn(scheduler)
                .map(ResponseEntity::ok);
    }

    private long fibonacci(long n) {
        if (n < 2) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
