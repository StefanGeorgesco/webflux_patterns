package fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto;

import java.util.UUID;

public record InventoryRequest(UUID paymentId,
                               int productId,
                               int quantity) {

    public static InventoryRequest of(UUID paymentId, int productId, int quantity) {
        return new InventoryRequest(paymentId, productId, quantity);
    }
}