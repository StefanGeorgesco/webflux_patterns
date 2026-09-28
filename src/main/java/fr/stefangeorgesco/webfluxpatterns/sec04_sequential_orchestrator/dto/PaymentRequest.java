package fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto;

import java.util.UUID;

public record PaymentRequest(int userId,
                             int amount,
                             UUID orderId) {

    public static PaymentRequest of(int userId, int amount, UUID orderId) {
        return new PaymentRequest(userId, amount, orderId);
    }
}