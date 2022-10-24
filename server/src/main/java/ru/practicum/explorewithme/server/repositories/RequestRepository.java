package ru.practicum.explorewithme.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.models.RequestStatus;
import ru.practicum.explorewithme.server.models.Request;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query("select count(r) from Request r where r.event.id=?1 and r.status=1")
    Long findConfirmedRequests(long eventId);

    Long countAllByEvent_IdAndStatus(long eventId, RequestStatus status);

    Optional<Request> findByRequestor_IdAndEvent_Id(long requestorId, long eventId);

    List<Request> findAllByRequestor_Id(long requestorId);
}
