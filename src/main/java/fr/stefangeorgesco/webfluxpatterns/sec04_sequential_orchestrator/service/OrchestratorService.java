package fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.service;

import fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto.OrchestrationRequestContext;
import fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto.OrderRequest;
import fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto.OrderResponse;
import fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.util.DebugUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto.Status.FAILED;
import static fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto.Status.SUCCESS;

@Service
public class OrchestratorService {

    private final OrderFulfillmentService orderFulfillmentService;
    private final OrderCancellationService orderCancellationService;

    public OrchestratorService(OrderFulfillmentService orderFulfillmentService,
                               OrderCancellationService orderCancellationService) {
        this.orderFulfillmentService = orderFulfillmentService;
        this.orderCancellationService = orderCancellationService;
    }

    public Mono<OrderResponse> placeOrder(Mono<OrderRequest> requestMono) {
        return requestMono
                .map(OrchestrationRequestContext::new)
                .flatMap(orderFulfillmentService::placeOrder)
                .doOnNext(this::checkRequestStatus)
                .doOnNext(DebugUtil::logRequestContext) // For debugging purposes
                .map(this::toOrderResponse);
    }

    private void checkRequestStatus(OrchestrationRequestContext ctx) {
        if (FAILED.equals(ctx.getStatus())) {
            orderCancellationService.cancelOrder(ctx);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private OrderResponse toOrderResponse(OrchestrationRequestContext ctx) {
        var isSuccess = SUCCESS.equals(ctx.getStatus());
        var address = isSuccess ? ctx.getShippingResponse().address() : null;
        var expectedDelivery = isSuccess ? ctx.getShippingResponse().expectedDelivery() : null;
        return OrderResponse.of(
                ctx.getOrderRequest().userId(),
                ctx.getOrderRequest().productId(),
                ctx.getOrderId(),
                ctx.getStatus(),
                address,
                expectedDelivery
        );
    }
}
