package fr.stefangeorgesco.webfluxpatterns.sec10_bulkhead;

import fr.stefangeorgesco.webfluxpatterns.sec10_bulkhead.dto.ProductAggregate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

/*
    Make sure to run the application before running this test.
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BulkheadTests {

    private final Logger log = LoggerFactory.getLogger(BulkheadTests.class);

    private WebClient client;

    @BeforeAll
    void setup() {
        client = WebClient.builder()
                .baseUrl("http://localhost:8080/")
                .build();
    }

    @Test
    void concurrentUsersTest() {
        StepVerifier.create(Flux.merge(fibonacciRequests(), productRequests()))
                .verifyComplete();
    }

    private Mono<Void> fibonacciRequests() {
        return Flux.range(1, 40)
                .flatMap(i -> client.get()
                        .uri("fibonacci/46")
                        .retrieve()
                        .bodyToMono(Long.class))
                .doOnNext(this::doLog)
                .then();
    }

    private Mono<Void> productRequests() {
        return Mono.delay(Duration.ofMillis(100))
                .thenMany(Flux.range(1, 40))
                .flatMap(i -> client.get()
                        .uri("product-aggregate/bulkhead/1")
                        .retrieve()
                        .bodyToMono(ProductAggregate.class))
                .map(ProductAggregate::category)
                .doOnNext(this::doLog)
                .then();
    }

    private void doLog(Object o) {
        if (log.isInfoEnabled()) {
            log.info("{}", o);
        }
    }
}
