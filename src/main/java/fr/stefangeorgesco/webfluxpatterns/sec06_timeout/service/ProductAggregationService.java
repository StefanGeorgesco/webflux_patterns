package fr.stefangeorgesco.webfluxpatterns.sec06_timeout.service;

import fr.stefangeorgesco.webfluxpatterns.sec06_timeout.client.ProductClient;
import fr.stefangeorgesco.webfluxpatterns.sec06_timeout.client.ReviewClient;
import fr.stefangeorgesco.webfluxpatterns.sec06_timeout.dto.Product;
import fr.stefangeorgesco.webfluxpatterns.sec06_timeout.dto.ProductAggregate;
import fr.stefangeorgesco.webfluxpatterns.sec06_timeout.dto.Review;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductAggregationService {

    private final ProductClient productClient;
    private final ReviewClient reviewClient;

    public ProductAggregationService(ProductClient productClient, ReviewClient reviewClient) {
        this.productClient = productClient;
        this.reviewClient = reviewClient;
    }

    // Specification: respond in less than 500ms in 90% of the cases.
    // See clients timeout implementation details.
    public Mono<ProductAggregate> aggregateProduct(int productId) {
        return Mono.zip(
                        productClient.getProduct(productId),
                        reviewClient.getReviews(productId)
                )
                .map(tuple ->
                        productAggregation(tuple.getT1(), tuple.getT2()));
    }

    private ProductAggregate productAggregation(Product product, List<Review> reviews) {
        return ProductAggregate.of(product.id(), product.category(), product.description(), reviews);
    }
}
