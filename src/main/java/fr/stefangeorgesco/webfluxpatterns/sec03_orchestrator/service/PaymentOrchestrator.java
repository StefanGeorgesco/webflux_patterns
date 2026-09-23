package fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.service;

import fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.client.UserClient;
import fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto.OrchestrationRequestContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

import static fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto.Status.SUCCESS;

@Service
public class PaymentOrchestrator extends Orchestrator {

    private final UserClient client;

    public PaymentOrchestrator(UserClient client) {
        this.client = client;
    }

    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext ctx) {
        return client.deduct(ctx.getPaymentRequest())
                .doOnNext(ctx::setPaymentResponse)
                .thenReturn(ctx);
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return ctx -> SUCCESS.equals(ctx.getPaymentResponse().status());
    }

    @Override
    public Consumer<OrchestrationRequestContext> cancel() {
        return ctx -> Mono.just(ctx)
                .filter(isSuccess())
                .map(OrchestrationRequestContext::getPaymentRequest)
                .flatMap(client::refund)
                .subscribe();
    }
}
