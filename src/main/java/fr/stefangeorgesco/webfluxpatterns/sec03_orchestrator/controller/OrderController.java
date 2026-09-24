package fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.controller;

import fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto.OrderRequest;
import fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto.OrderResponse;
import fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.service.OrchestratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("orders")
public class OrderController {

    private final OrchestratorService service;

    public OrderController(OrchestratorService service) {
        this.service = service;
    }

    @PostMapping("place-order")
    public Mono<ResponseEntity<OrderResponse>> placeOrder(@RequestBody Mono<OrderRequest> orderRequest) {
        return service.placeOrder(orderRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
