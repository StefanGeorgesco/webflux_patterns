package fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.util;

import fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto.InventoryRequest;
import fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto.OrchestrationRequestContext;
import fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto.PaymentRequest;
import fr.stefangeorgesco.webfluxpatterns.sec04_sequential_orchestrator.dto.ShippingRequest;

public class OrchestrationUtil {

    private OrchestrationUtil() {
    }

    public static void buildRequestContext(OrchestrationRequestContext ctx) {
        buildPaymentRequest(ctx);
        buildInventoryRequest(ctx);
        buildShippingRequest(ctx);
    }

    private static void buildPaymentRequest(OrchestrationRequestContext ctx) {
        var orderRequest = ctx.getOrderRequest();
        var paymentRequest = PaymentRequest.of(
                orderRequest.userId(),
                ctx.getProductPrice() * orderRequest.quantity(),
                ctx.getOrderId()
        );
        ctx.setPaymentRequest(paymentRequest);
    }

    private static void buildInventoryRequest(OrchestrationRequestContext ctx) {
        var orderRequest = ctx.getOrderRequest();
        var inventoryRequest = InventoryRequest.of(
                ctx.getPaymentResponse().paymentId(),
                orderRequest.productId(),
                orderRequest.quantity()
        );
        ctx.setInventoryRequest(inventoryRequest);
    }

    private static void buildShippingRequest(OrchestrationRequestContext ctx) {
        var orderRequest = ctx.getOrderRequest();
        var shippingRequest = ShippingRequest.of(
                ctx.getInventoryResponse().inventoryId(),
                orderRequest.quantity(),
                orderRequest.userId()
        );
        ctx.setShippingRequest(shippingRequest);
    }
}
