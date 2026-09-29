package fr.stefangeorgesco.webfluxpatterns.sec08_circuit_breaker.dto;

public record Review(int id,
                     String user,
                     int rating,
                     String comment) {
}