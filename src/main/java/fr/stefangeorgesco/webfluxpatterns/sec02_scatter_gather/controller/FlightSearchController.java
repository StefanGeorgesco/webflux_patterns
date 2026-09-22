package fr.stefangeorgesco.webfluxpatterns.sec02_scatter_gather.controller;

import fr.stefangeorgesco.webfluxpatterns.sec02_scatter_gather.dto.FlightResult;
import fr.stefangeorgesco.webfluxpatterns.sec02_scatter_gather.service.FlightSearchService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/flights")
public class FlightSearchController {

    private final FlightSearchService service;

    public FlightSearchController(FlightSearchService service) {
        this.service = service;
    }

    @GetMapping(value = "{from}/{to}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<FlightResult> searchFlights(@PathVariable String from, @PathVariable String to) {
        return service.searchFlights(from, to);
    }
}
