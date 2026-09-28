package fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.service;

import fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.client.ProductClient;
import fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto.OrchestrationRequestContext;
import fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto.Product;
import fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.util.OrchestrationUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto.Status.FAILED;
import static fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto.Status.SUCCESS;

@Service
public class OrderFulfillmentService {

    private final ProductClient productClient;
    private final PaymentOrchestrator paymentOrchestrator;
    private final InventoryOrchestrator inventoryOrchestrator;
    private final ShippingOrchestrator shippingOrchestrator;

    public OrderFulfillmentService(ProductClient productClient, PaymentOrchestrator paymentOrchestrator, InventoryOrchestrator inventoryOrchestrator, ShippingOrchestrator shippingOrchestrator) {
        this.productClient = productClient;
        this.paymentOrchestrator = paymentOrchestrator;
        this.inventoryOrchestrator = inventoryOrchestrator;
        this.shippingOrchestrator = shippingOrchestrator;
    }

    public Mono<OrchestrationRequestContext> placeOrder(OrchestrationRequestContext ctx) {
        return getProductPrice(ctx)
                .doOnNext(OrchestrationUtil::buildPaymentRequest)
                .flatMap(paymentOrchestrator::create)
                .doOnNext(OrchestrationUtil::buildInventoryRequest)
                .flatMap(inventoryOrchestrator::create)
                .doOnNext(OrchestrationUtil::buildShippingRequest)
                .flatMap(shippingOrchestrator::create)
                .doOnNext(c -> c.setStatus(SUCCESS))
                .doOnError(c -> ctx.setStatus(FAILED))
                .onErrorReturn(ctx);
    }

    private Mono<OrchestrationRequestContext> getProductPrice(OrchestrationRequestContext ctx) {
        return productClient.getProduct(ctx.getOrderRequest().productId())
                .map(Product::price)
                .doOnNext(ctx::setProductPrice)
                .map(price -> ctx);
    }
}
