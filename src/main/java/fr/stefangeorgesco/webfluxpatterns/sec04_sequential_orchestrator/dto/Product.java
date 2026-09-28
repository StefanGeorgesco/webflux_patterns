package fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto;

public record Product(int id,
                      String category,
                      String description,
                      int price) {

    public static Product of(int id, String category, String description, int price) {
        return new Product(id, category, description, price);
    }
}