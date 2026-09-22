package fr.stefangeorgesco.webfluxpatterns.sec02_scatter_gather.client;

import fr.stefangeorgesco.webfluxpatterns.sec02_scatter_gather.dto.FlightResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class JetBlueClient {

    private static final String AIRLINE = "JETBLUE";
    private final WebClient client;

    public JetBlueClient(@Value("${sec02.jetblue-service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<FlightResult> getFlights(String from, String to) {
        return client.get()
                .uri("{from}/{to}", from, to)
                .retrieve()
                .bodyToFlux(FlightResult.class)
                .map(result -> normalized(result, from, to))
                .onErrorResume(e -> Flux.empty());
    }

    private FlightResult normalized(FlightResult result, String from, String to) {
        return FlightResult.of(AIRLINE, from, to, result.price(), result.date());
    }
}
