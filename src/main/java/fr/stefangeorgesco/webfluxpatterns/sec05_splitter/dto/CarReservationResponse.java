package fr.stefangeorgesco.webfluxpatterns.sec05_splitter.dto;

import java.time.LocalDate;
import java.util.UUID;

public record CarReservationResponse(UUID reservationId,
                                     CarCategory category,
                                     String city,
                                     LocalDate pickup,
                                     LocalDate drop,
                                     int price) {
}