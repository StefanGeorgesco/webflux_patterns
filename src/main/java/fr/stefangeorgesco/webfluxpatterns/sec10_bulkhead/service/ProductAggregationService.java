package fr.stefangeorgesco.webfluxpatterns.sec10_bulkhead.service;

import fr.stefangeorgesco.webfluxpatterns.sec10_bulkhead.client.ProductClient;
import fr.stefangeorgesco.webfluxpatterns.sec10_bulkhead.client.ReviewClient;
import fr.stefangeorgesco.webfluxpatterns.sec10_bulkhead.dto.Product;
import fr.stefangeorgesco.webfluxpatterns.sec10_bulkhead.dto.ProductAggregate;
import fr.stefangeorgesco.webfluxpatterns.sec10_bulkhead.dto.Review;
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
