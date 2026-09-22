package fr.stefangeorgesco.webfluxpatterns.sec02_scatter_gather.dto;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

public record FlightResult(@Nullable String airline,
                           @Nullable String from,
                           @Nullable String to,
                           double price,
                           LocalDate date) {

    public static FlightResult of(String airline, String from, String to, double price, LocalDate date) {
        return new FlightResult(airline, from, to, price, date);
    }
}
