package fr.stefangeorgesco.webfluxpatterns.sec08_circuit_breaker.dto;

public record Product(int id,
                      String category,
                      String description,
                      double price) {
}