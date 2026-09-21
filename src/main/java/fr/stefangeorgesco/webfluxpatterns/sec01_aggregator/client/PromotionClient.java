package fr.stefangeorgesco.webfluxpatterns.sec01_aggregator.client;

import fr.stefangeorgesco.webfluxpatterns.sec01_aggregator.dto.PromotionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PromotionClient {

    private final WebClient client;

    public PromotionClient(@Value("${sec01.promotion-service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<PromotionResponse> getPromotion(int productId) {
        return client.get()
                .uri("{id}", productId)
                .retrieve()
                .bodyToMono(PromotionResponse.class);
    }
}
