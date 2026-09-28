package fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto;

public record PaymentResponse(int userId,
                              String name,
                              int balance,
                              Status status) {

    public static PaymentResponse of(int userId, String name, int balance, Status status) {
        return new PaymentResponse(userId, name, balance, status);
    }
}