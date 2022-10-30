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
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.statistics.StatisticMapper.toEndpointHit;
import static ru.practicum.explorewithme.statistics.StatisticMapper.toStatistic;

@Service
@Slf4j
@Transactional
@AllArgsConstructor(onConstructor_ = @Autowired)
public class StatsServiceImpl implements StatsService {

    private final StatisticRepository repository;
    @Override
    public EndpointHit saveStats(EndpointHit endpointHit) {
        Statistic statistic = toStatistic(endpointHit);
        return toEndpointHit(repository.save(statistic));
    }

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        return Arrays.stream(uris).map(uri -> {
            Statistic statistic = repository.findFirstByUri(uri).orElseThrow(() ->
                    new StatisticNotFoundException("Статистика по данному URI не найдена"));
            Long views = getViews(start, end, uri, unique);
            return new ViewStats(statistic.getApp(), uri, views);
        }).collect(Collectors.toList());
    }

    @Override
    public List<ViewStats> getStats(String queryString, String[] uris, boolean unique) throws UnsupportedEncodingException {
        ViewsParamDecoder decoder = new ViewsParamDecoder(queryString);
        LocalDateTime start = decoder.getStart();
        LocalDateTime end = decoder.getEnd();

        return Arrays.stream(uris).map(uri -> {
            Statistic statistic = repository.findFirstByUri(uri).orElseThrow(() ->
                    new StatisticNotFoundException("Статистика по данному URI не найдена"));
            Long views = getViews(start, end, uri, unique);
            return new ViewStats(statistic.getApp(), uri, views);
        }).collect(Collectors.toList());
    }

    private Long getViews(LocalDateTime start, LocalDateTime end, String uri, boolean unique) {
        return unique ? repository.getViewsByParamDistinct(start, end, uri).get() :
                repository.getViewsByParam(start, end, uri).get();
    }

    private String decode(String value) throws UnsupportedEncodingException {
        return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
    }

//    private BooleanExpression makeSearchExpression(QStatistic statistic, LocalDateTime start, LocalDateTime end,
//                                                   String[] uris, Boolean unique) {
//        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
////        List<BooleanExpression> list = new ArrayList<>();
////
////        list.add(statistic.timestamp.between(start, end));
////
////        if (uris != null && uris.length != 0) {
////            list.add(statistic.uri.in(uris));
////        }
////
////        if (unique) {
////            list.add(statistic.countDistinct()));
////        }
//        List<ComparableExpressionBase> list = new ArrayList<>();
//        list.add(statistic.timestamp.between(start, end));
//
//        if (uris != null && uris.length != 0) {
//            list.add(statistic.uri.in(uris));
//        }
//        if (unique) {
//            list.add(statistic.ip.countDistinct());
//        }
//        return list.stream().reduce(BooleanExpression::a)
//
//    }
}
