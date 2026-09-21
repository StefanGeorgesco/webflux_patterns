package fr.stefangeorgesco.webfluxpatterns.sec01_aggregator.dto;

public record ProductResponse(int id,
                              String category,
                              String description,
                              double price) {
}