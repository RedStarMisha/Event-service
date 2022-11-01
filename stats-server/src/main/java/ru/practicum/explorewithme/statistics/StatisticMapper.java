package ru.practicum.explorewithme.statistics;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.models.statistics.EndpointHit;

import java.time.LocalDateTime;

@Component
public class StatisticMapper {

    public static Statistic toStatistic(EndpointHit endpointHit) {
        String[] path = endpointHit.getUri().split("/");
        long eventId = Long.parseLong(path[path.length - 1]);
        return new Statistic(endpointHit.getApp(), endpointHit.getUri(), endpointHit.getIp(), LocalDateTime.now(),
                eventId);
    }
    public static EndpointHit toEndpointHit(Statistic statistic) {
        return new EndpointHit(statistic.getId(), statistic.getApp(), statistic.getUri(), statistic.getIp(),
                statistic.getTimestamp());
    }
}
