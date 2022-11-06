package ru.practicum.explorewithme.server.services.priv.strategy;

import org.springframework.data.domain.Pageable;
import ru.practicum.explorewithme.models.event.EventState;
import ru.practicum.explorewithme.server.models.Event;
import ru.practicum.explorewithme.server.models.User;

import java.time.LocalDateTime;
import java.util.List;

public interface Strategy {

    EventState getState();

    List<Event> findEventsByStrategy(User user, LocalDateTime start, LocalDateTime end, Pageable page);
}
