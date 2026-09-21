package fr.stefangeorgesco.webfluxpatterns.sec01_aggregator.client;

import fr.stefangeorgesco.webfluxpatterns.sec01_aggregator.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductClient {

    private final WebClient client;

    public ProductClient(@Value("${sec01.product-service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<ProductResponse> getProduct(int productId) {
        return client.get()
                .uri("{id}", productId)
                .retrieve()
                .bodyToMono(ProductResponse.class);
    }
}
