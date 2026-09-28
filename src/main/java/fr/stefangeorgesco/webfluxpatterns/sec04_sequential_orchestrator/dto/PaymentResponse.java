package fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto;

import java.util.UUID;

public record PaymentResponse(UUID paymentId,
                              int userId,
                              String name,
                              int balance,
                              Status status) {

    public static PaymentResponse of(UUID paymentId, int userId, String name, int balance, Status status) {
        return new PaymentResponse(paymentId, userId, name, balance, status);
    }
}