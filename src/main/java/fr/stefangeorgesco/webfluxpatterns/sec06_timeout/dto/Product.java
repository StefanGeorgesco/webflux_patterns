package fr.stefangeorgesco.webfluxpatterns.sec06_timeout.dto;

public record Product(int id,
                      String category,
                      String description,
                      double price) {
}