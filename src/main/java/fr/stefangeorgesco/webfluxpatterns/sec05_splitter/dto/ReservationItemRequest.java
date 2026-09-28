package fr.stefangeorgesco.webfluxpatterns.sec05_splitter.dto;

import java.time.LocalDate;

public record ReservationItemRequest(ReservationType type,
                                     String category,
                                     String city,
                                     LocalDate from,
                                     LocalDate to) {

    public static ReservationItemRequest of(ReservationType type, String category, String city, LocalDate from,
                                            LocalDate to) {
        return new ReservationItemRequest(type, category, city, from, to);
    }
}
