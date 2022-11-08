package ru.practicum.explorewithme.server.repositories;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.models.event.State;
import ru.practicum.explorewithme.server.models.Event;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {
    Optional<Event> findByInitiator_IdAndId(long initiatorId, long eventId);

    List<Event> findAllByInitiator_Id(long initiatorId, Pageable pageable);

    Optional<Event> findByIdAndState(long eventId, State state);

//    @Query("update Event e set e.numberConfirmed=?1 where e.id=?2")
//    void addConfirmedRequest(int increment, long eventId);

    @Query(value = "update events as e set number_confirmed = number_confirmed + 1 where id = ?1 returning e" +
            ".number_confirmed", nativeQuery = true)
    void addConfirmedRequest(long eventId);
}
