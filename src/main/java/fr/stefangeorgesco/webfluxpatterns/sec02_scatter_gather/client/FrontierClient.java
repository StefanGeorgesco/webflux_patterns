package fr.stefangeorgesco.webfluxpatterns.sec02_scatter_gather.client;

import fr.stefangeorgesco.webfluxpatterns.sec02_scatter_gather.dto.FlightResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
public class FrontierClient {

    private final WebClient client;

    public FrontierClient(@Value("${sec02.frontier-service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<FlightResult> getFlights(String from, String to) {
        return client.post()
                .bodyValue(Map.of("from", from, "to", to))
                .retrieve()
                .bodyToFlux(FlightResult.class)
                .onErrorResume(e -> Flux.empty());
    }
}
