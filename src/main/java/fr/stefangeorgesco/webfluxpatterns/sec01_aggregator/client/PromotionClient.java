package fr.stefangeorgesco.webfluxpatterns.sec01_aggregator.client;

import fr.stefangeorgesco.webfluxpatterns.sec01_aggregator.dto.PromotionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.ZoneId;

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
                .bodyToMono(PromotionResponse.class)
                .onErrorReturn(defaultPromotionResponse(productId));
    }

    /*
        Helper method
     */

    private PromotionResponse defaultPromotionResponse(int productId) {
        return PromotionResponse.of(productId, "unavailable", 0.0, LocalDate.now(ZoneId.systemDefault()));
    }
}
