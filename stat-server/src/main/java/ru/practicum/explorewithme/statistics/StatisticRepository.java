package ru.practicum.explorewithme.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long>, MyStatRepository {

    Optional<Statistic> findFirstByUri(String uri);

//    @Query("select count(s.ip) from Statistic s where s.ip in (select distinct st.ip from Statistic st) and (s.timestamp between ?1 and ?2)")
//    Statistic findDistinctByAppAnd();
//
//    List<Statistic> findAllByTimestampBetweenAndUriIn(LocalDateTime start, LocalDateTime end, String[] uris);
//
//    List<Statistic> findDistinctByIp(BooleanExpression expression);

}
