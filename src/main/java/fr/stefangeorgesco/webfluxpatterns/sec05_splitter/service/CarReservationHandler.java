package fr.stefangeorgesco.webfluxpatterns.sec05_splitter.service;

import fr.stefangeorgesco.webfluxpatterns.sec05_splitter.client.CarClient;
import fr.stefangeorgesco.webfluxpatterns.sec05_splitter.dto.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CarReservationHandler extends ReservationHandler {

    private final CarClient client;

    public CarReservationHandler(CarClient client) {
        this.client = client;
    }

    @Override
    protected ReservationType getReservationType() {
        return ReservationType.CAR;
    }

    @Override
    protected Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> reservationRequestFlux) {
        return reservationRequestFlux
                .map(this::toCarReservationRequest)
                .transform(client::reserve)
                .map(this::toReservationItemResponse);
    }

    private CarReservationRequest toCarReservationRequest(ReservationItemRequest request) {
        return CarReservationRequest.of(
                CarCategory.valueOf(request.category()),
                request.city(),
                request.from(),
                request.to()
        );
    }

    private ReservationItemResponse toReservationItemResponse(CarReservationResponse carReservationResponse) {
        return ReservationItemResponse.of(
                carReservationResponse.reservationId(),
                getReservationType(),
                carReservationResponse.category().name(),
                carReservationResponse.city(),
                carReservationResponse.pickup(),
                carReservationResponse.drop(),
                carReservationResponse.price()
        );
    }
}
