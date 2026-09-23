package fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto;

import java.util.UUID;

public record ShippingRequest(int quantity,
                              int userId,
                              UUID orderId) {

    public static ShippingRequest of(int quantity, int userId, UUID orderId) {
        return new ShippingRequest(quantity, userId, orderId);
    }
}