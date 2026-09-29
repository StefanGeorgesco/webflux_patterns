package fr.stefangeorgesco.webfluxpatterns.sec06_timeout.dto;

public record Review(int id,
                     String user,
                     int rating,
                     String comment) {
}