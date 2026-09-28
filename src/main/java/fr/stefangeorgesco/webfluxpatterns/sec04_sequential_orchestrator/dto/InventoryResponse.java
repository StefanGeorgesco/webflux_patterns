package fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto;

public record InventoryResponse(int productId,
                                int quantity,
                                int remainingQuantity,
                                Status status) {

    public static InventoryResponse of(int productId, int quantity, int remainingQuantity, Status status) {
        return new InventoryResponse(productId, quantity, remainingQuantity, status);
    }
}