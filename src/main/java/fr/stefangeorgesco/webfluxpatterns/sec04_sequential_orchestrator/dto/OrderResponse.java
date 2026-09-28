package fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto;

import java.util.UUID;

public record OrderResponse(int userId,
                            int productId,
                            UUID orderId,
                            Status status,
                            Address shippingAddress,
                            String expectedDelivery) {

    public static OrderResponse of(int userId, int productId, UUID orderId, Status status, Address shippingAddress,
                                   String expectedDelivery) {
        return new OrderResponse(userId, productId, orderId, status, shippingAddress, expectedDelivery);
    }
}