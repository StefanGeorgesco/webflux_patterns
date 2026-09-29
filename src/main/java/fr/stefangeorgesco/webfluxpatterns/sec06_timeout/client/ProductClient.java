package fr.stefangeorgesco.webfluxpatterns.sec06_timeout.client;

import fr.stefangeorgesco.webfluxpatterns.sec06_timeout.dto.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductClient {

    private final WebClient client;

    public ProductClient(@Value("${sec06.product-service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<Product> getProduct(int productId) {
        return client.get()
                .uri("{id}", productId)
                .retrieve()
                .bodyToMono(Product.class)
                .onErrorResume(e -> Mono.empty());
    }
}
