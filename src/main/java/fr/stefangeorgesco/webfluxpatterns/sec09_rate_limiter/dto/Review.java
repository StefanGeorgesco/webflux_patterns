package fr.stefangeorgesco.webfluxpatterns.sec09_rate_limiter.dto;

public record Review(int id,
                     String user,
                     int rating,
                     String comment) {
}