package fr.stefangeorgesco.webfluxpatterns.sec09_rate_limiter.dto;

public record Product(int id,
                      String category,
                      String description,
                      double price) {
}