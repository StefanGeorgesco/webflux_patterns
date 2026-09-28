package fr.stefangeorgesco.webfluxpatterns.sec05_splitter.dto;

import java.util.List;
import java.util.UUID;

public record ReservationResponse(UUID reservationId,
                                  List<ReservationItemResponse> items,
                                  int price) {

    public static ReservationResponse of(UUID reservationId, List<ReservationItemResponse> items, int price) {
        return new ReservationResponse(reservationId, items, price);
    }
}
