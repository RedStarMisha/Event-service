package ru.practicum.explorewithme.server.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.server.models.Group;
import ru.practicum.explorewithme.server.models.Request;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    Optional<Request> findByRequestor_IdAndEvent_Id(long requestorId, long eventId);

    List<Request> findAllByRequestor_Id(long requestorId);

    List<Request> findAllByEvent_Id(long eventId);

    @Query("select r.event.id from Request r where r.requestor.id = ?1 and r.status = 1")
    List<Long> findEventIdsWhereRequestStatusConfirmed(long requestorId);

    @Query("select r.event.id from Request r where r.requestor.id = ?1 and r.status = 1 and ?2 member of r.groups ")
    List<Long> findEventIdsWhereRequestStatusConfirmedAndGroup(long requestorId, Group group);

    @Query("update Request r set r.status=2 where r.event.id=?1 and r.status=0")
    void rejectedAllRequestsByEventId(long eventId);

    @Query(value = "select pr.event " +
            "from participation_requests pr " +
            "left outer join request_group rg on pr.id = rg.request" +
            " join groups g on g.id = rg.group_level " +
            "join events e on e.id = pr.event " +
            "where e.initiator=?1 and (g=?2 or g.title='FRIENDS_ALL')", nativeQuery = true)
    List<Long> find(long requestorId, Group group, Pageable pageable);

    @Query("from Request r where r.id=?1 and r.requestor.id=?2 and r.status=1")
    Optional<Request> findByIdAndRequestorIdAndStateConfirmed(long requestId, long userId);
}
