package ru.practicum.explorewithme.server.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.models.event.State;
import ru.practicum.explorewithme.server.models.Event;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByInitiator_IdAndId(long initiatorId, long eventId);

    List<Event> findAllByInitiator_Id(long initiatorId, Pageable pageable);

    Optional<Event> findByIdAndState(long eventId, State state);

    @Query("update Event e set e.numberConfirmed = e.numberConfirmed + 1 where e.id=?1")
    void addConfirmedRequest(long eventId);

}
