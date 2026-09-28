package fr.stefangeorgesco.webfluxpatterns.sec05_splitter.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ReservationItemResponse(UUID reservationItemId,
                                      ReservationType type,
                                      String category,
                                      String city,
                                      LocalDate from,
                                      LocalDate to,
                                      int price) {

    public static ReservationItemResponse of(UUID reservationItemId,
                                             ReservationType type,
                                             String category,
                                             String city,
                                             LocalDate from,
                                             LocalDate to,
                                             int price) {
        return new ReservationItemResponse(reservationItemId, type, category, city, from, to, price);
    }
}
