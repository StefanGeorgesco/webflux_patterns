package fr.stefangeorgesco.webfluxpatterns.sec08_circuit_breaker.client;

import fr.stefangeorgesco.webfluxpatterns.sec08_circuit_breaker.dto.Review;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
public class ReviewClient {

    private final WebClient client;

    public ReviewClient(@Value("${sec08.review-service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<List<Review>> getReviews(int productId) {
        return client.get()
                .uri("{id}", productId)
                .retrieve()
                // Do not retry on 4xx errors, as they are client errors and retrying won't help
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                .bodyToFlux(Review.class)
                .collectList()
                // Use retryWhen to adapt the retry strategy
                .retry(5)
                // When retrying, add a timeout to avoid waiting too long for the response
                .timeout(Duration.ofMillis(300))
                .onErrorReturn(List.of());
    }
}
