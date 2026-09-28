package fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto;

import java.util.UUID;

public record ShippingResponse(UUID shippingId,
                               int quantity,
                               Status status,
                               String expectedDelivery,
                               Address address) {

    public static ShippingResponse of(UUID shippingId, int quantity, Status status, String expectedDelivery,
                                      Address address) {
        return new ShippingResponse(shippingId, quantity, status, expectedDelivery, address);
    }

}