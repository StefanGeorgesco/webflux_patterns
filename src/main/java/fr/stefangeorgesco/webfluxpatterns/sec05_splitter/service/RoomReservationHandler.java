package fr.stefangeorgesco.webfluxpatterns.sec05_splitter.service;

import fr.stefangeorgesco.webfluxpatterns.sec05_splitter.client.RoomClient;
import fr.stefangeorgesco.webfluxpatterns.sec05_splitter.dto.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class RoomReservationHandler extends ReservationHandler {

    private final RoomClient client;

    public RoomReservationHandler(RoomClient client) {
        this.client = client;
    }

    @Override
    protected ReservationType getReservationType() {
        return ReservationType.ROOM;
    }

    @Override
    protected Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> reservationRequestFlux) {
        return reservationRequestFlux
                .map(this::toRoomReservationRequest)
                .transform(client::reserve)
                .map(this::toReservationItemResponse);
    }

    private RoomReservationRequest toRoomReservationRequest(ReservationItemRequest request) {
        return RoomReservationRequest.of(
                RoomCategory.valueOf(request.category()),
                request.city(),
                request.from(),
                request.to()
        );
    }

    private ReservationItemResponse toReservationItemResponse(RoomReservationResponse roomReservationResponse) {
        return ReservationItemResponse.of(
                roomReservationResponse.reservationId(),
                getReservationType(),
                roomReservationResponse.category().name(),
                roomReservationResponse.city(),
                roomReservationResponse.checkIn(),
                roomReservationResponse.checkOut(),
                roomReservationResponse.price()
        );
    }
}
