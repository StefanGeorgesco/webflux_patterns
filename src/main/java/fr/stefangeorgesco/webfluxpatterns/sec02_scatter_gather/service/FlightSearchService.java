package fr.stefangeorgesco.webfluxpatterns.sec02_scatter_gather.service;

import fr.stefangeorgesco.webfluxpatterns.sec02_scatter_gather.client.DeltaClient;
import fr.stefangeorgesco.webfluxpatterns.sec02_scatter_gather.client.FrontierClient;
import fr.stefangeorgesco.webfluxpatterns.sec02_scatter_gather.client.JetBlueClient;
import fr.stefangeorgesco.webfluxpatterns.sec02_scatter_gather.dto.FlightResult;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
public class FlightSearchService {

    private final DeltaClient deltaClient;
    private final FrontierClient frontierClient;
    private final JetBlueClient jetBlueClient;

    public FlightSearchService(DeltaClient deltaClient, FrontierClient frontierClient, JetBlueClient jetBlueClient) {
        this.deltaClient = deltaClient;
        this.frontierClient = frontierClient;
        this.jetBlueClient = jetBlueClient;
    }

    public Flux<FlightResult> searchFlights(String from, String to) {
        return Flux.merge(
                        deltaClient.getFlights(from, to),
                        frontierClient.getFlights(from, to),
                        jetBlueClient.getFlights(from, to)
                )
                .take(Duration.ofSeconds(5));
    }
}
