package fr.stefangeorgesco.webfluxpatterns.sec05_splitter.controller;

import fr.stefangeorgesco.webfluxpatterns.sec05_splitter.dto.ReservationItemRequest;
import fr.stefangeorgesco.webfluxpatterns.sec05_splitter.dto.ReservationResponse;
import fr.stefangeorgesco.webfluxpatterns.sec05_splitter.service.ReservationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reservations")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @PostMapping("reserve")
    public Mono<ReservationResponse> reserve(@RequestBody Flux<ReservationItemRequest> reservationRequestFlux) {
        return service.reserve(reservationRequestFlux);
    }
}
