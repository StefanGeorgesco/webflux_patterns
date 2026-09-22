package fr.stefangeorgesco.webfluxpatterns.sec01_aggregator.service;

import fr.stefangeorgesco.webfluxpatterns.sec01_aggregator.client.ProductClient;
import fr.stefangeorgesco.webfluxpatterns.sec01_aggregator.client.PromotionClient;
import fr.stefangeorgesco.webfluxpatterns.sec01_aggregator.client.ReviewClient;
import fr.stefangeorgesco.webfluxpatterns.sec01_aggregator.dto.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductAggregationService {

    private final ProductClient productClient;
    private final PromotionClient promotionClient;
    private final ReviewClient reviewClient;

    public ProductAggregationService(ProductClient productClient, PromotionClient promotionClient,
                                     ReviewClient reviewClient) {
        this.productClient = productClient;
        this.promotionClient = promotionClient;
        this.reviewClient = reviewClient;
    }

    public Mono<ProductAggregate> aggregateProduct(int productId) {
        return Mono.zip(
                        productClient.getProduct(productId),
                        promotionClient.getPromotion(productId),
                        reviewClient.getReviews(productId)
                )
                .map(tuple ->
                        productAggregation(tuple.getT1(), tuple.getT2(), tuple.getT3()));
    }

    private ProductAggregate productAggregation(ProductResponse product, PromotionResponse promotion,
                                                List<Review> reviews) {
        double listPrice = product.price();
        double discount = promotion.discount();
        double discountedPrice = rounded(listPrice * (1 - discount / 100));
        double amountSaved = rounded(listPrice * discount / 100);
        Price price = new Price(listPrice, discount, discountedPrice, amountSaved, promotion.endDate());
        return ProductAggregate.of(product.id(), product.category(), product.description(), price, reviews);
    }

    private double rounded(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
