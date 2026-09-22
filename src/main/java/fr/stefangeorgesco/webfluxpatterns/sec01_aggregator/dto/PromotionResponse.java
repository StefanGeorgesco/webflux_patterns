package fr.stefangeorgesco.webfluxpatterns.sec01_aggregator.dto;

import java.time.LocalDate;

public record PromotionResponse(int id,
                                String type,
                                double discount,
                                LocalDate endDate) {

    public static PromotionResponse of(int id, String type, double discount, LocalDate endDate) {
        return new PromotionResponse(id, type, discount, endDate);
    }
}