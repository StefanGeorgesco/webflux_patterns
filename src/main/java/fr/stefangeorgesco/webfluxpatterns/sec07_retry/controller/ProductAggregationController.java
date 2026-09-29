package fr.stefangeorgesco.webfluxpatterns.sec07_retry.controller;

import fr.stefangeorgesco.webfluxpatterns.sec07_retry.dto.ProductAggregate;
import fr.stefangeorgesco.webfluxpatterns.sec07_retry.service.ProductAggregationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product-aggregate/retry")
public class ProductAggregationController {

    private final ProductAggregationService service;

    public ProductAggregationController(ProductAggregationService service) {
        this.service = service;
    }

    @GetMapping("{productId}")
    public Mono<ResponseEntity<ProductAggregate>> aggregateProduct(@PathVariable int productId) {
        return service.aggregateProduct(productId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
