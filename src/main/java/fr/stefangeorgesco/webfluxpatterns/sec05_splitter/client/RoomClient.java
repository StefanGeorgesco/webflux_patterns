package fr.stefangeorgesco.webfluxpatterns.sec05_splitter.client;

import fr.stefangeorgesco.webfluxpatterns.sec05_splitter.dto.RoomReservationRequest;
import fr.stefangeorgesco.webfluxpatterns.sec05_splitter.dto.RoomReservationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class RoomClient {

    private static final String RESERVE = "reserve";
    private final WebClient client;

    public RoomClient(@Value("${sec05.room-service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<RoomReservationResponse> reserve(Flux<RoomReservationRequest> request) {
        return client.post()
                .uri(RESERVE)
                .body(request, RoomReservationRequest.class)
                .retrieve()
                .bodyToFlux(RoomReservationResponse.class)
                .onErrorResume(e -> Flux.empty());
    }
}
