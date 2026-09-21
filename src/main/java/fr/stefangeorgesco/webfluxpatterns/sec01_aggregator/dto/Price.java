package fr.stefangeorgesco.webfluxpatterns.sec01_aggregator.dto;

import java.time.LocalDate;

public record Price(double listPrice,
                    double discount,
                    double discountedPrice,
                    double amountSaved,
                    LocalDate endDate) {
}