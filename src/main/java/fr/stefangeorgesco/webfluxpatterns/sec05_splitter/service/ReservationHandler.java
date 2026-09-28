package fr.stefangeorgesco.webfluxpatterns.sec05_splitter.service;

import fr.stefangeorgesco.webfluxpatterns.sec05_splitter.dto.ReservationItemRequest;
import fr.stefangeorgesco.webfluxpatterns.sec05_splitter.dto.ReservationItemResponse;
import fr.stefangeorgesco.webfluxpatterns.sec05_splitter.dto.ReservationType;
import reactor.core.publisher.Flux;

public abstract class ReservationHandler {

    protected abstract ReservationType getReservationType();

    protected abstract Flux<ReservationItemResponse> reserve(Flux<ReservationItemRequest> reservationRequestFlux);
}
