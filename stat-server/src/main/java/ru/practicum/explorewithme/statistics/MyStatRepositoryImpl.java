package ru.practicum.explorewithme.statistics;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;

public class MyStatRepositoryImpl implements MyStatRepository {
    private final EntityManager em;

    @Autowired
    public MyStatRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Long> getViewsByParamDistinct(LocalDateTime start, LocalDateTime end, String uri) {
        QStatistic statistic = QStatistic.statistic;
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        return queryFactory.select(statistic.ip.countDistinct()).where(statistic.uri.eq(uri)
                .and(statistic.timestamp.between(start, end))).stream().findAny();
    }

    @Override
    public Optional<Long> getViewsByParam(LocalDateTime start, LocalDateTime end, String uri) {
        QStatistic statistic = QStatistic.statistic;
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        return queryFactory.select(statistic.count()).from(statistic).where(statistic.uri.eq(uri)
                .and(statistic.timestamp.between(start, end))).stream().findAny();
    }
}
