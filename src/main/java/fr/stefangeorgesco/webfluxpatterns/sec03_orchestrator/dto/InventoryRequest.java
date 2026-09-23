package fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto;

import java.util.UUID;

public record InventoryRequest(UUID orderId,
                               int productId,
                               int quantity) {

    public static InventoryRequest of(UUID orderId, int productId, int quantity) {
        return new InventoryRequest(orderId, productId, quantity);
    }
}