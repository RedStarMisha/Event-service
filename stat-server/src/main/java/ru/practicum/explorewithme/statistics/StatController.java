package ru.practicum.explorewithme.statistics;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.models.statistics.EndpointHit;
import ru.practicum.explorewithme.models.statistics.ViewStats;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@AllArgsConstructor(onConstructor_ = @Autowired)
public class StatController {

    private final StatsService service;

    @PostMapping
    public EndpointHit saveStats(@RequestBody @Valid EndpointHit hit) {
        return service.saveStats(hit);
    }

    @GetMapping
    public ViewStats getStats(@RequestParam(name = "start") LocalDateTime start,
                              @RequestParam(name = "end") LocalDateTime end,
                              @RequestParam(name = "uris", required = false) String[] uris,
                              @RequestParam(name = "unique", required = false) Boolean unique) {
        return service.getStats(start, end, uris, unique);
    }



}
