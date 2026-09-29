package fr.stefangeorgesco.webfluxpatterns.sec07_retry.dto;

public record Product(int id,
                      String category,
                      String description,
                      double price) {
}