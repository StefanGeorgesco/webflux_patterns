package fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.service;

import fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.client.InventoryClient;
import fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto.OrchestrationRequestContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

import static fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto.Status.SUCCESS;

@Service
public class InventoryOrchestrator extends Orchestrator {

    private final InventoryClient client;

    public InventoryOrchestrator(InventoryClient client) {
        this.client = client;
    }

    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext ctx) {
        return client.deduct(ctx.getInventoryRequest())
                .doOnNext(ctx::setInventoryResponse)
                .thenReturn(ctx);
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return ctx -> SUCCESS.equals(ctx.getInventoryResponse().status());
    }

    @Override
    public Consumer<OrchestrationRequestContext> cancel() {
        return ctx -> Mono.just(ctx)
                .filter(isSuccess())
                .map(OrchestrationRequestContext::getInventoryRequest)
                .flatMap(client::restore)
                .subscribe();
    }
}
