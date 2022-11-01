package ru.practicum.explorewithme.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.server.models.Request;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    Optional<Request> findByRequestor_IdAndEvent_Id(long requestorId, long eventId);

    List<Request> findAllByRequestor_Id(long requestorId);

    List<Request> findAllByEvent_Id(long eventId);
    @Query("update Request r set r.status=2 where r.event.id=?1 and r.status=0")
    void rejectedAllRequestsByEventId(long eventId);
}
