package ru.practicum.explorewithme.server.repositories;


import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.models.event.State;
import ru.practicum.explorewithme.server.models.Event;
import ru.practicum.explorewithme.server.models.QEvent;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {
    Optional<Event> findByInitiator_IdAndId(long initiatorId, long eventId);

    List<Event> findAllByInitiator_Id(long initiatorId, Pageable pageable);

    Optional<Event> findByIdAndState(long eventId, State state);

    @Query("update Event e set e.numberConfirmed=?1 where e.id=?2")
    void addConfirmedRequest(int increment, long eventId);
}
