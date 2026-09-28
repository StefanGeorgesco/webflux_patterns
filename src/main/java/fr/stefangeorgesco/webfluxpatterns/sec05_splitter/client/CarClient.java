package fr.stefangeorgesco.webfluxpatterns.sec05_splitter.client;

import fr.stefangeorgesco.webfluxpatterns.sec05_splitter.dto.CarReservationRequest;
import fr.stefangeorgesco.webfluxpatterns.sec05_splitter.dto.CarReservationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class CarClient {

    private static final String RESERVE = "reserve";
    private final WebClient client;

    public CarClient(@Value("${sec05.car-service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<CarReservationResponse> reserve(Flux<CarReservationRequest> request) {
        return client.post()
                .uri(RESERVE)
                .body(request, CarReservationRequest.class)
                .retrieve()
                .bodyToFlux(CarReservationResponse.class)
                .onErrorResume(e -> Flux.empty());
    }
}
