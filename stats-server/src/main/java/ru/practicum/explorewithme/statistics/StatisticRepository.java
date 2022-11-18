package ru.practicum.explorewithme.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long>, MyStatRepository {

    Optional<Statistic> findFirstByUri(String uri);
}
