package ru.practicum.explorewithme.statistics;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MyStatRepository {
    Optional<Long> getViewsByParamDistinct(LocalDateTime start, LocalDateTime end, String uri);

    Optional<Long> getViewsByParam(LocalDateTime start, LocalDateTime end, String uri);
}
