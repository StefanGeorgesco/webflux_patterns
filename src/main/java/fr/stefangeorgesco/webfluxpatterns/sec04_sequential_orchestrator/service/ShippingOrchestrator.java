package fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.service;

import fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.client.ShippingClient;
import fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto.OrchestrationRequestContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto.Status.SUCCESS;

@Service
public class ShippingOrchestrator extends Orchestrator {

    private final ShippingClient client;

    public ShippingOrchestrator(ShippingClient client) {
        this.client = client;
    }

    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext ctx) {
        return client.schedule(ctx.getShippingRequest())
                .doOnNext(ctx::setShippingResponse)
                .thenReturn(ctx)
                .handle(statusHandler());
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return ctx -> Objects.nonNull(ctx.getShippingResponse())
                && SUCCESS.equals(ctx.getShippingResponse().status());
    }

    @Override
    public Consumer<OrchestrationRequestContext> cancel() {
        return ctx -> Mono.just(ctx)
                .filter(isSuccess())
                .map(OrchestrationRequestContext::getShippingRequest)
                .flatMap(client::cancel)
                .subscribe();
    }
}
