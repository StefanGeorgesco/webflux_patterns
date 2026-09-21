package fr.stefangeorgesco.webfluxpatterns.sec01_aggregator.dto;

import java.util.List;

public record ProductAggregate(int id,
                               String category,
                               String description,
                               Price price,
                               List<Review> reviews) {

    public static ProductAggregate of(int id, String category, String description, Price price,
                                      List<Review> reviews) {
        return new ProductAggregate(id, category, description, price, reviews);
    }
}