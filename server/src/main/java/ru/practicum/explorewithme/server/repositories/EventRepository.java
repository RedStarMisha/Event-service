package ru.practicum.explorewithme.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.server.admin.event.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
