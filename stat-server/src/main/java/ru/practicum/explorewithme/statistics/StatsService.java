package ru.practicum.explorewithme.statistics;

import ru.practicum.explorewithme.models.statistics.EndpointHit;
import ru.practicum.explorewithme.models.statistics.ViewStats;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    EndpointHit saveStats(EndpointHit endpointHit);

    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique);

    List<ViewStats> getStats(String queryString, String[] uris, boolean unique) throws UnsupportedEncodingException;
}
