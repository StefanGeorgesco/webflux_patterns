package fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto;

public record Address(String street,
                      String city,
                      String state,
                      String zipCode) {

    public static Address of(String street, String city, String state, String zipCode) {
        return new Address(street, city, state, zipCode);
    }
}