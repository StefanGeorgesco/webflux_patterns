package fr.stefangeorgesco.webfluxpatterns.sec05_splitter.dto;

import java.time.LocalDate;

public record RoomReservationRequest(RoomCategory category,
                                     String city,
                                     LocalDate checkIn,
                                     LocalDate checkOut) {

    public static RoomReservationRequest of(RoomCategory category, String city, LocalDate checkIn, LocalDate checkOut) {
        return new RoomReservationRequest(category, city, checkIn, checkOut);
    }
}