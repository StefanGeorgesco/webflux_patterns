package fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto;

public record OrderRequest(int userId,
                           int productId,
                           int quantity) {

    public static OrderRequest of(int userId, int productId, int quantity) {
        return new OrderRequest(userId, productId, quantity);
    }
}