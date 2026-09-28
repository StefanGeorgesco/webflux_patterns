package fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.client;

import fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto.InventoryRequest;
import fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto.InventoryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto.Status.FAILED;

@Service
public class InventoryClient {

    private static final String DEDUCT = "deduct";
    private static final String RESTORE = "restore";
    private final WebClient client;

    public InventoryClient(@Value("${sec04.inventory-service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<InventoryResponse> deduct(InventoryRequest request) {
        return callService(DEDUCT, request);
    }

    public Mono<InventoryResponse> restore(InventoryRequest request) {
        return callService(RESTORE, request);
    }

    private Mono<InventoryResponse> callService(String endpoint, InventoryRequest request) {
        return client.post()
                .uri(endpoint)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(InventoryResponse.class)
                .onErrorReturn(errorResponse(request));
    }

    private InventoryResponse errorResponse(InventoryRequest request) {
        return InventoryResponse.of(request.productId(), request.quantity(), 0, FAILED);
    }
}
