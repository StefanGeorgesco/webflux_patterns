package fr.stefangeorgesco.webfluxpatterns.sec05_splitter.service;

import fr.stefangeorgesco.webfluxpatterns.sec05_splitter.dto.ReservationItemRequest;
import fr.stefangeorgesco.webfluxpatterns.sec05_splitter.dto.ReservationItemResponse;
import fr.stefangeorgesco.webfluxpatterns.sec05_splitter.dto.ReservationResponse;
import fr.stefangeorgesco.webfluxpatterns.sec05_splitter.dto.ReservationType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final Map<ReservationType, ReservationHandler> reservationHandlerMap;

    public ReservationService(List<ReservationHandler> reservationHandlers) {
        this.reservationHandlerMap = reservationHandlers.stream()
                .collect(Collectors.toMap(
                        ReservationHandler::getReservationType,
                        Function.identity())
                );
    }

    public Mono<ReservationResponse> reserve(Flux<ReservationItemRequest> reservationRequestFlux) {
        return reservationRequestFlux
                .groupBy(ReservationItemRequest::type)
                .flatMap(this::aggregator)
                .collectList()
                .map(this::toReservationResponse);
    }

    private Flux<ReservationItemResponse> aggregator(GroupedFlux<ReservationType, ReservationItemRequest> groupedFlux) {
        var reservationType = groupedFlux.key();
        var handler = reservationHandlerMap.get(reservationType);
        if (Objects.isNull(handler)) {
            return Flux.error(new IllegalArgumentException("No handler found for reservation type: " + reservationType));
        }
        return handler.reserve(groupedFlux);
    }

    private ReservationResponse toReservationResponse(List<ReservationItemResponse> reservationItemResponses) {
        return ReservationResponse.of(
                UUID.randomUUID(),
                reservationItemResponses,
                reservationItemResponses.stream()
                        .mapToInt(ReservationItemResponse::price)
                        .sum()
        );
    }
}
