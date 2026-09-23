package fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.service;

import fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto.OrchestrationRequestContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

import static fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto.Status.FAILED;
import static fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto.Status.SUCCESS;

@Service
public class OrderFulfillmentService {

    private final List<Orchestrator> orchestrators;

    public OrderFulfillmentService(List<Orchestrator> orchestrators) {
        this.orchestrators = orchestrators;
    }

    public Mono<OrchestrationRequestContext> placeOrder(OrchestrationRequestContext ctx) {
        var createMonos = orchestrators.stream()
                .map(o -> o.create(ctx))
                .toList();

        // orchestrators all return the same context, so we can just take the first one
        return Mono.zip(createMonos, a -> a[0])
                .cast(OrchestrationRequestContext.class)
                .doOnNext(this::updateStatus);
    }

    private void updateStatus(OrchestrationRequestContext ctx) {
        var succeeded = orchestrators.stream().allMatch(o -> o.isSuccess().test(ctx));
        var status = succeeded ? SUCCESS : FAILED;
        ctx.setStatus(status);
    }
}
