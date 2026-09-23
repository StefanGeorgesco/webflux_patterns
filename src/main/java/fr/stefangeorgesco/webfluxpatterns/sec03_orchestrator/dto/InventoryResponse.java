package fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto;

public record InventoryResponse(int productId,
                                int quantity,
                                int remainingQuantity,
                                Status status) {

    public static InventoryResponse of(int productId, int quantity, int remainingQuantity, Status status) {
        return new InventoryResponse(productId, quantity, remainingQuantity, status);
    }
}