package ru.practicum.explorewithme.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.models.subscription.FriendshipGroup;
import ru.practicum.explorewithme.server.models.Request;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RequestRepository extends JpaRepository<Request, Long> {
    Optional<Request> findByRequestor_IdAndEvent_Id(long requestorId, long eventId);

    List<Request> findAllByRequestor_Id(long requestorId);

    List<Request> findAllByEvent_Id(long eventId);

    @Query("select r.event.id from Request r where r.requestor.id = ?1 and r.status = 1")
    List<Long> findEventIdsWhereRequestStatusConfirmed(long requestorId);

    @Query("select r.event.id from Request r where r.requestor.id = ?1 and r.status = 1 and ?2 member of r.groups")
    List<Long> findEventIdsWhereRequestStatusConfirmedAndGroup(long requestorId, FriendshipGroup group);

    @Query("update Request r set r.status=2 where r.event.id=?1 and r.status=0")
    void rejectedAllRequestsByEventId(long eventId);
}
