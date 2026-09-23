package fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.service;

import fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.client.ProductClient;
import fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto.OrchestrationRequestContext;
import fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto.OrderRequest;
import fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto.OrderResponse;
import fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto.Product;
import fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.util.OrchestrationUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto.Status.FAILED;
import static fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto.Status.SUCCESS;

@Service
public class OrchestratorService {

    private final ProductClient productClient;
    private final OrderFulfillmentService orderFulfillmentService;
    private final OrderCancellationService orderCancellationService;

    public OrchestratorService(ProductClient productClient, OrderFulfillmentService orderFulfillmentService,
                               OrderCancellationService orderCancellationService) {
        this.productClient = productClient;
        this.orderFulfillmentService = orderFulfillmentService;
        this.orderCancellationService = orderCancellationService;
    }

    public Mono<OrderResponse> placeOrder(Mono<OrderRequest> requestMono) {
        return requestMono
                .map(OrchestrationRequestContext::new)
                .flatMap(this::getProductPrice)
                .doOnNext(OrchestrationUtil::buildRequestContext)
                .flatMap(orderFulfillmentService::placeOrder)
                .doOnNext(this::checkRequestStatus)
                .map(this::toOrderResponse);
    }

    private Mono<OrchestrationRequestContext> getProductPrice(OrchestrationRequestContext ctx) {
        return productClient.getProduct(ctx.getOrderRequest().productId())
                .map(Product::price)
                .doOnNext(ctx::setProductPrice)
                .thenReturn(ctx);
    }

    private void checkRequestStatus(OrchestrationRequestContext ctx) {
        if (FAILED.equals(ctx.getStatus())) {
            orderCancellationService.cancelOrder(ctx);
        }
    }

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
