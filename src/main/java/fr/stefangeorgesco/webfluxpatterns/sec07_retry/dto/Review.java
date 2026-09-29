package fr.stefangeorgesco.webfluxpatterns.sec07_retry.dto;

public record Review(int id,
                     String user,
                     int rating,
                     String comment) {
}