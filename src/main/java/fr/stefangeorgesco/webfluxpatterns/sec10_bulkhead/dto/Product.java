package fr.stefangeorgesco.webfluxpatterns.sec10_bulkhead.dto;

public record Product(int id,
                      String category,
                      String description,
                      double price) {
}