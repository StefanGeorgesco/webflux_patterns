package fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.service;

import fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto.OrchestrationRequestContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
public class OrderCancellationService {

    private final Sinks.Many<OrchestrationRequestContext> sink;
    private final Flux<OrchestrationRequestContext> flux;

    public OrderCancellationService(List<Orchestrator> orchestrators) {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
        this.flux = sink.asFlux().publishOn(Schedulers.boundedElastic());
        orchestrators.forEach(o -> flux.subscribe(o.cancel()));
    }

    public void cancelOrder(OrchestrationRequestContext ctx) {
        this.sink.tryEmitNext(ctx);
    }
}
