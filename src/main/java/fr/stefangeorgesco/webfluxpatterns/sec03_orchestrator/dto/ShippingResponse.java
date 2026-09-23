package fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto;

import java.util.UUID;

public record ShippingResponse(UUID orderId,
                               int quantity,
                               Status status,
                               String expectedDelivery,
                               Address address) {

    public static ShippingResponse of(UUID orderId, int quantity, Status status, String expectedDelivery,
                                      Address address) {
        return new ShippingResponse(orderId, quantity, status, expectedDelivery, address);
    }

}