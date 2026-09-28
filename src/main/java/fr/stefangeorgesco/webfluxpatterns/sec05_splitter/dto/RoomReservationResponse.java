package fr.stefangeorgesco.webfluxpatterns.sec05_splitter.dto;

import java.time.LocalDate;
import java.util.UUID;

public record RoomReservationResponse(UUID reservationId,
                                      RoomCategory category,
                                      String city,
                                      LocalDate checkIn,
                                      LocalDate checkOut,
                                      int price) {
}