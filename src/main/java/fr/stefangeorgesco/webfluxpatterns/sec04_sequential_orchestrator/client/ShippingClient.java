package fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.client;

import fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto.ShippingRequest;
import fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto.ShippingResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto.Status.FAILED;

@Service
public class ShippingClient {

    public static final String SCHEDULE = "schedule";
    public static final String CANCEL = "cancel";
    private final WebClient client;

    public ShippingClient(@Value("${sec04.shipping-service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<ShippingResponse> schedule(ShippingRequest request) {
        return callService(SCHEDULE, request);
    }

    // Will emit complete signal on success (no shipping response)
    public Mono<ShippingResponse> cancel(ShippingRequest request) {
        return callService(CANCEL, request);
    }

    private Mono<ShippingResponse> callService(String endpoint, ShippingRequest request) {
        return client.post()
                .uri(endpoint)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ShippingResponse.class)
                .onErrorReturn(errorResponse(request));
    }

    private ShippingResponse errorResponse(ShippingRequest request) {
        return ShippingResponse.of(null, request.quantity(), FAILED, null, null);
    }
}
