package fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.client;

import fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto.PaymentRequest;
import fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto.PaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto.Status.FAILED;

@Service
public class UserClient {

    private static final String DEDUCT = "deduct";
    private static final String REFUND = "refund";
    private final WebClient client;

    public UserClient(@Value("${sec03.user-service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<PaymentResponse> deduct(PaymentRequest request) {
        return callService(DEDUCT, request);
    }

    public Mono<PaymentResponse> refund(PaymentRequest request) {
        return callService(REFUND, request);
    }

    private Mono<PaymentResponse> callService(String endpoint, PaymentRequest request) {
        return client.post()
                .uri(endpoint)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .onErrorReturn(errorResponse(request));
    }

    private PaymentResponse errorResponse(PaymentRequest request) {
        return PaymentResponse.of(request.userId(), null, 0, FAILED);
    }
}
