package ru.practicum.explorewithme.statistics;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.models.statistics.EndpointHit;
import ru.practicum.explorewithme.models.statistics.ViewStats;
import ru.practicum.explorewithme.util.ViewsParamDecoder;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.statistics.StatisticMapper.toEndpointHit;
import static ru.practicum.explorewithme.statistics.StatisticMapper.toStatistic;

@Service
@Slf4j
@AllArgsConstructor(onConstructor_ = @Autowired)
@Transactional
public class StatsServiceImpl implements StatsService {

    private final StatisticRepository repository;

    @Override
    public EndpointHit saveStats(EndpointHit endpointHit) {
        Statistic statistic = toStatistic(endpointHit);
        return toEndpointHit(repository.save(statistic));
    }

    @Override
    public List<ViewStats> getStats(String queryString, String[] uris, boolean unique)
            throws UnsupportedEncodingException {
        ViewsParamDecoder decoder = new ViewsParamDecoder(queryString);
        LocalDateTime start = decoder.getStart();
        LocalDateTime end = decoder.getEnd();

        return Arrays.stream(uris).map(repository::findFirstByUri).filter(Optional::isPresent).map(Optional::get)
                .map(st -> new ViewStats(st.getApp(), st.getUri(), getViews(start, end, st.getUri(), unique)))
                .collect(Collectors.toList());
    }

    private Long getViews(LocalDateTime start, LocalDateTime end, String uri, boolean unique) {
        return unique ? repository.getViewsByParamDistinct(start, end, uri).get() :
                repository.getViewsByParam(start, end, uri).get();
    }
}
