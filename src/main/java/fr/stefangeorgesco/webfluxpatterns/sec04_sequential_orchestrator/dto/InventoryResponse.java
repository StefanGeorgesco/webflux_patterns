package fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto;

import java.util.UUID;

public record InventoryResponse(UUID inventoryId,
                                int productId,
                                int quantity,
                                int remainingQuantity,
                                Status status) {

    public static InventoryResponse of(UUID inventoryId, int productId, int quantity, int remainingQuantity,
                                       Status status) {
        return new InventoryResponse(inventoryId, productId, quantity, remainingQuantity, status);
    }
}