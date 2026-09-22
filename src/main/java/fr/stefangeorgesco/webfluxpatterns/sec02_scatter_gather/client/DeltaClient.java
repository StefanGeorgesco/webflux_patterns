package fr.stefangeorgesco.webfluxpatterns.sec02_scatter_gather.client;

import fr.stefangeorgesco.webfluxpatterns.sec02_scatter_gather.dto.FlightResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class DeltaClient {

    private final WebClient client;

    public DeltaClient(@Value("${sec02.delta-service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<FlightResult> getFlights(String from, String to) {
        return client.get()
                .uri("{from}/{to}", from, to)
                .retrieve()
                .bodyToFlux(FlightResult.class)
                .onErrorResume(e -> Flux.empty());
    }
}
