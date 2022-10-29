package ru.practicum.explorewithme.statistics;

import ru.practicum.explorewithme.models.statistics.EndpointHit;
import ru.practicum.explorewithme.models.statistics.ViewStats;

import java.time.LocalDateTime;

public interface StatsService {
    EndpointHit saveStats(EndpointHit endpointHit);

    ViewStats getStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique);
}
