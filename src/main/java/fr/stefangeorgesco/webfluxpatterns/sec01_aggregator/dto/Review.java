package fr.stefangeorgesco.webfluxpatterns.sec01_aggregator.dto;

public record Review(int id,
                     String user,
                     int rating,
                     String comment) {
}