package fr.stefangeorgesco.webfluxpatterns.sec01_aggregator.client;

import fr.stefangeorgesco.webfluxpatterns.sec01_aggregator.dto.Review;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ReviewClient {

    private final WebClient client;

    public ReviewClient(@Value("${sec01.review-service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<List<Review>> getReviews(int productId) {
        return client.get()
                .uri("{id}", productId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
    }
}
