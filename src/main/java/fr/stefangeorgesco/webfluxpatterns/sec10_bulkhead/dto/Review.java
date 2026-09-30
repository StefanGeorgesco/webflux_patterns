package fr.stefangeorgesco.webfluxpatterns.sec10_bulkhead.dto;

public record Review(int id,
                     String user,
                     int rating,
                     String comment) {
}