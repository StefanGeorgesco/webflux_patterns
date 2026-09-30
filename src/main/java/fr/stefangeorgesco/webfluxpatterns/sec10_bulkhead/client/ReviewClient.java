package fr.stefangeorgesco.webfluxpatterns.sec10_bulkhead.client;

import fr.stefangeorgesco.webfluxpatterns.sec10_bulkhead.dto.Review;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ReviewClient {

    private final WebClient client;

    public ReviewClient(@Value("${sec10.review-service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<List<Review>> getReviews(int productId) {
        return client.get()
                .uri("{id}", productId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                .bodyToFlux(Review.class)
                .collectList()
                .onErrorReturn(List.of());
    }
}
