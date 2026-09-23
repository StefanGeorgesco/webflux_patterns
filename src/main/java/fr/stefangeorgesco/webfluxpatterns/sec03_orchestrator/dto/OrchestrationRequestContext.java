package fr.stefangeorgesco.webfluxpatterns.sec03_orchestrator.dto;

import java.util.UUID;

@SuppressWarnings("unused")
public class OrchestrationRequestContext {

    private final UUID orderId = UUID.randomUUID();

    private OrderRequest orderRequest;
    private int productPrice;
    private PaymentRequest paymentRequest;
    private PaymentResponse paymentResponse;
    private InventoryRequest inventoryRequest;
    private InventoryResponse inventoryResponse;
    private ShippingRequest shippingRequest;
    private ShippingResponse shippingResponse;
    private Status status;

    public OrchestrationRequestContext() {
    }

    public OrchestrationRequestContext(OrderRequest orderRequest) {
        this.orderRequest = orderRequest;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public OrderRequest getOrderRequest() {
        return orderRequest;
    }

    public void setOrderRequest(OrderRequest orderRequest) {
        this.orderRequest = orderRequest;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public PaymentRequest getPaymentRequest() {
        return paymentRequest;
    }

    public void setPaymentRequest(PaymentRequest paymentRequest) {
        this.paymentRequest = paymentRequest;
    }

    public PaymentResponse getPaymentResponse() {
        return paymentResponse;
    }

    public void setPaymentResponse(PaymentResponse paymentResponse) {
        this.paymentResponse = paymentResponse;
    }

    public InventoryRequest getInventoryRequest() {
        return inventoryRequest;
    }

    public void setInventoryRequest(InventoryRequest inventoryRequest) {
        this.inventoryRequest = inventoryRequest;
    }

    public InventoryResponse getInventoryResponse() {
        return inventoryResponse;
    }

    public void setInventoryResponse(InventoryResponse inventoryResponse) {
        this.inventoryResponse = inventoryResponse;
    }

    public ShippingRequest getShippingRequest() {
        return shippingRequest;
    }

    public void setShippingRequest(ShippingRequest shippingRequest) {
        this.shippingRequest = shippingRequest;
    }

    public ShippingResponse getShippingResponse() {
        return shippingResponse;
    }

    public void setShippingResponse(ShippingResponse shippingResponse) {
        this.shippingResponse = shippingResponse;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrchestrationRequestContext{" +
                "orderId=" + orderId +
                ", orderRequest=" + orderRequest +
                ", productPrice=" + productPrice +
                ", paymentRequest=" + paymentRequest +
                ", paymentResponse=" + paymentResponse +
                ", inventoryRequest=" + inventoryRequest +
                ", inventoryResponse=" + inventoryResponse +
                ", shippingRequest=" + shippingRequest +
                ", shippingResponse=" + shippingResponse +
                ", status=" + status +
                '}';
    }
}
