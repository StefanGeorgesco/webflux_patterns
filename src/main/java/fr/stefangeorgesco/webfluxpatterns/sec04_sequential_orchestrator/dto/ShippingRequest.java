package fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto;

import java.util.UUID;

public record ShippingRequest(UUID inventoryId,
                              int quantity,
                              int userId
) {

    public static ShippingRequest of(UUID inventoryId, int quantity, int userId) {
        return new ShippingRequest(inventoryId, quantity, userId);
    }
}