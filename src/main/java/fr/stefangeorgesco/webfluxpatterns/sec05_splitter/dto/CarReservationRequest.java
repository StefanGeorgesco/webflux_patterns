package fr.stefangeorgesco.webfluxpatterns.sec05_splitter.dto;

import java.time.LocalDate;

public record CarReservationRequest(CarCategory category,
                                    String city,
                                    LocalDate pickup,
                                    LocalDate drop) {

    public static CarReservationRequest of(CarCategory category, String city, LocalDate pickup, LocalDate drop) {
        return new CarReservationRequest(category, city, pickup, drop);
    }
}