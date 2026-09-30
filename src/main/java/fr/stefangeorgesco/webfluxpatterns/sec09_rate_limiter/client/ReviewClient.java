package fr.stefangeorgesco.webfluxpatterns.sec09_rate_limiter.client;

import fr.stefangeorgesco.webfluxpatterns.sec09_rate_limiter.dto.Review;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ReviewClient {

    private static final Logger log = LoggerFactory.getLogger(ReviewClient.class);
    private final Map<Integer, List<Review>> cache;
    private final WebClient client;

    public ReviewClient(@Value("${sec09.review-service}") String baseUrl) {
        this.client = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
        this.cache = new ConcurrentHashMap<>();
    }

    @RateLimiter(name = "review-service", fallbackMethod = "getReviewsFallback")
    public Mono<List<Review>> getReviews(int productId) {
        return client.get()
                .uri("{id}", productId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                .bodyToFlux(Review.class)
                .collectList()
                .doOnNext(reviews -> cache.put(productId, reviews));
    }

    @SuppressWarnings("unused")
    public Mono<List<Review>> getReviewsFallback(int productId, Throwable throwable) {
        if (log.isWarnEnabled()) {
            log.warn("Rate limit exceeded for productId: {} because of: {}", productId, throwable.getMessage());
        }
        return Mono.fromSupplier(() -> cache.getOrDefault(productId, List.of()));
    }
}
