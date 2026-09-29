package fr.stefangeorgesco.webfluxpatterns.sec06_timeout.dto;

import java.util.List;

public record ProductAggregate(int id,
                               String category,
                               String description,
                               List<Review> reviews) {

    public static ProductAggregate of(int id, String category, String description,
                                      List<Review> reviews) {
        return new ProductAggregate(id, category, description, reviews);
    }
}